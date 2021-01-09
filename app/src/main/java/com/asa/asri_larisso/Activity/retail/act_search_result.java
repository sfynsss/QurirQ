package com.asa.asri_larisso.Activity.retail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.asa.asri_larisso.Api.Api;
import com.asa.asri_larisso.Api.RetrofitClient;
import com.asa.asri_larisso.R;
import com.asa.asri_larisso.Response.BaseResponse;
import com.asa.asri_larisso.Session.Session;
import com.asa.asri_larisso.Table.Barang;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_search_result extends AppCompatActivity {

    ImageView btn_back;
    Button btn_search;
    RecyclerView list_barang;
    EditText cari_brg;
    Handler handler = new Handler();
    ShimmerFrameLayout shimmer;

    Api api;
    Session session;
    Call<BaseResponse<Barang>> getBarang;

    ArrayList<String> kd_brg = new ArrayList<>();
    ArrayList<String> nm_brg = new ArrayList<>();
    ArrayList<String> kat_brg = new ArrayList<>();
    ArrayList<String> hrg_brg = new ArrayList<>();
    ArrayList<String> harga_asli = new ArrayList<>();
    ArrayList<String> satuan = new ArrayList<>();
    ArrayList<String> gambar = new ArrayList<>();

    AdapterBarang adapterBarang;
    NumberFormat formatRupiah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_search_result);

        btn_back    = findViewById(R.id.btn_back);
        btn_search  = findViewById(R.id.btn_search);
        list_barang = findViewById(R.id.list_barang);
        cari_brg = findViewById(R.id.cari_brg);
        shimmer = findViewById(R.id.shimmer);

        Locale localeID = new Locale("in", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(act_search_result.this, act_home_retail.class));
            }
        });

        session = new Session(act_search_result.this);
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());
        shimmer.setVisibility(View.GONE);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_barang.setVisibility(View.GONE);
                shimmer.setVisibility(View.VISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        shimmer.stopShimmer();
                        shimmer.hideShimmer();
                        shimmer.setVisibility(View.GONE);
                        list_barang.setVisibility(View.VISIBLE);
                    }
                },2850);
                getData(cari_brg.getText().toString());
            }
        });

    }

    public void getData(String nama_barang) {
        getBarang = api.getBarangByName(nama_barang);
        getBarang.enqueue(new Callback<BaseResponse<Barang>>() {
            @Override
            public void onResponse(Call<BaseResponse<Barang>> call, Response<BaseResponse<Barang>> response) {
                if (response.isSuccessful()) {
                    kd_brg.clear();
                    nm_brg.clear();
                    kat_brg.clear();
                    hrg_brg.clear();
                    satuan.clear();
                    gambar.clear();
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        kd_brg.add(response.body().getData().get(i).getKdBrg());
                        nm_brg.add(response.body().getData().get(i).getNmBrg());
                        kat_brg.add(getIntent().getStringExtra("judul"));
                        hrg_brg.add(formatRupiah.format(response.body().getData().get(i).getHargaJl()));
                        harga_asli.add(response.body().getData().get(i).getHargaJl().toString());
                        satuan.add(response.body().getData().get(i).getSatuan1());
                        gambar.add(response.body().getData().get(i).getGambar());
                    }
                    adapterBarang = new AdapterBarang(act_search_result.this, act_search_result.this, kd_brg, gambar, nm_brg, kat_brg, hrg_brg, satuan, harga_asli);
                    list_barang.setLayoutManager(new GridLayoutManager(act_search_result.this, 2));
                    list_barang.setAdapter(adapterBarang);
                } else {
                    Toasty.error(act_search_result.this, "Data Tidak Ditemukan !!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Barang>> call, Throwable t) {
                Toasty.error(act_search_result.this, "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}