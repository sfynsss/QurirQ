package com.asa.asri_larisso.Activity.retail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asa.asri_larisso.Api.Api;
import com.asa.asri_larisso.Api.RetrofitClient;
import com.asa.asri_larisso.R;
import com.asa.asri_larisso.Response.BaseResponse;
import com.asa.asri_larisso.Session.Session;
import com.asa.asri_larisso.Table.Barang;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_browse_diskon extends AppCompatActivity {

    ImageView back;
    TextView nama_kategori;
    RecyclerView recyclerBarang;
    Api api;
    Session session;
    Call<BaseResponse<Barang>> getBarang;

    ArrayList<String> kd_brg = new ArrayList<>();
    ArrayList<String> nm_brg = new ArrayList<>();
    ArrayList<String> kat_brg = new ArrayList<>();
    ArrayList<String> hrg_brg = new ArrayList<>();
    ArrayList<String> harga_asli = new ArrayList<>();
    ArrayList<String> harga_jl2 = new ArrayList<>();
    ArrayList<String> harga_jl3 = new ArrayList<>();
    ArrayList<String> harga_jl4 = new ArrayList<>();
    ArrayList<String> qty_min2 = new ArrayList<>();
    ArrayList<String> qty_min3 = new ArrayList<>();
    ArrayList<String> qty_min4 = new ArrayList<>();
    ArrayList<String> satuan = new ArrayList<>();
    ArrayList<String> gambar = new ArrayList<>();
    ArrayList<String> disc = new ArrayList<>();
    ArrayList<String> harga_disc = new ArrayList<>();

    AdapterBarang adapterBarang;
    NumberFormat formatRupiah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_browse_diskon);

        back = findViewById(R.id.back);
        nama_kategori = findViewById(R.id.nama_kategori);
        recyclerBarang = findViewById(R.id.recycle_barang);

        Locale localeID = new Locale("in", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        session = new Session(act_browse_diskon.this);
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());
        getData();

    }

    public void getData() {
        getBarang = api.getBarangDiskon(session.getKdOutlet()+"");
        getBarang.enqueue(new Callback<BaseResponse<Barang>>() {
            @Override
            public void onResponse(Call<BaseResponse<Barang>> call, Response<BaseResponse<Barang>> response) {
                if (response.isSuccessful()) {
                    kd_brg.clear();
                    nm_brg.clear();
                    kat_brg.clear();
                    hrg_brg.clear();
                    harga_asli.clear();
                    harga_jl2.clear();
                    harga_jl3.clear();
                    harga_jl4.clear();
                    qty_min2.clear();
                    qty_min3.clear();
                    qty_min4.clear();
                    satuan.clear();
                    gambar.clear();
                    disc.clear();
                    harga_disc.clear();

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        kd_brg.add(response.body().getData().get(i).getKdBrg());
                        nm_brg.add(response.body().getData().get(i).getNmBrg());
                        kat_brg.add(getIntent().getStringExtra("judul"));
                        hrg_brg.add(formatRupiah.format(response.body().getData().get(i).getHargaJl()));
                        harga_asli.add(response.body().getData().get(i).getHargaJl().toString());
                        harga_jl2.add(response.body().getData().get(i).getHargaJl2().toString());
                        harga_jl3.add(response.body().getData().get(i).getHargaJl3().toString());
                        harga_jl4.add(response.body().getData().get(i).getHargaJl4().toString());
                        qty_min2.add(response.body().getData().get(i).getQtyMin2().toString());
                        qty_min3.add(response.body().getData().get(i).getQtyMin3().toString());
                        qty_min4.add(response.body().getData().get(i).getQtyMin4().toString());
                        satuan. add(response.body().getData().get(i).getSatuan1());
                        gambar.add(response.body().getData().get(i).getGambar());
                        disc.add(response.body().getData().get(i).getDisc().toString());
                        harga_disc.add(response.body().getData().get(i).getHargaDisc().toString());
                    }

                    adapterBarang = new AdapterBarang(act_browse_diskon.this, act_browse_diskon.this,
                            kd_brg,
                            gambar,
                            nm_brg,
                            kat_brg,
                            harga_asli,
                            hrg_brg,
                            harga_jl2,
                            harga_jl3,
                            harga_jl4,
                            qty_min2,
                            qty_min3,
                            qty_min4,
                            satuan,
                            disc,
                            harga_disc);
                    recyclerBarang.setLayoutManager(new GridLayoutManager(act_browse_diskon.this, 2));
                    recyclerBarang.setAdapter(adapterBarang);

                } else {
                    Toasty.error(act_browse_diskon.this, "Data Tidak Ditemukan !!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Barang>> call, Throwable t) {
                Toasty.error(act_browse_diskon.this, "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}