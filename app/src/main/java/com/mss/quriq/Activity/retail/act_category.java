package com.mss.quriq.Activity.retail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.mss.quriq.Api.Api;
import com.mss.quriq.Api.RetrofitClient;
import com.mss.quriq.R;
import com.mss.quriq.Response.BaseResponse;
import com.mss.quriq.Session.Session;
import com.mss.quriq.Table.KategoriOutlet;
import com.mss.quriq.Table.kategori;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_category extends AppCompatActivity {

    ImageView back;
    Api api;
    Session session;
    Call<BaseResponse<KategoriOutlet>> getKategori;

    ArrayList<String> kd_kategori = new ArrayList<>();
    ArrayList<String> judul = new ArrayList<>();
    ArrayList<String> gambar = new ArrayList<>();

    RecyclerView kategoriBarang;
    AdapterKategoriBarang adapterKategori;
    Handler handler = new Handler();
    ShimmerFrameLayout shimmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_category);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        kategoriBarang = findViewById(R.id.kategori_barang);
        shimmer = findViewById(R.id.shimmer);

        session = new Session(act_category.this);
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());
        getKategori = api.getKategoriOutlet("all");

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                shimmer.stopShimmer();
                shimmer.hideShimmer();
                shimmer.setVisibility(View.GONE);
                kategoriBarang.setVisibility(View.VISIBLE);
            }
        }, 2850);

        getKategori.enqueue(new Callback<BaseResponse<KategoriOutlet>>() {
            @Override
            public void onResponse(Call<BaseResponse<KategoriOutlet>> call, Response<BaseResponse<KategoriOutlet>> response) {
                if (response.isSuccessful()) {
                    kd_kategori.clear();
                    judul.clear();
                    gambar.clear();

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        kd_kategori.add(response.body().getData().get(i).getId().toString());
                        judul.add(response.body().getData().get(i).getNmKategoriOutlet());
                        gambar.add(response.body().getData().get(i).getGbrKategoriOutlet());
                    }

                    adapterKategori = new AdapterKategoriBarang(act_category.this, act_category.this, kd_kategori, judul, gambar);
                    kategoriBarang.setLayoutManager(new GridLayoutManager(act_category.this, 3));
                    kategoriBarang.setAdapter(adapterKategori);
                } else {
                    Toasty.error(act_category.this, "Error Bad Response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<KategoriOutlet>> call, Throwable t) {
                Toasty.error(act_category.this, "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}