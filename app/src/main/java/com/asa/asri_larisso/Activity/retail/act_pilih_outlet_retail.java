package com.asa.asri_larisso.Activity.retail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.asa.asri_larisso.Api.Api;
import com.asa.asri_larisso.Api.RetrofitClient;
import com.asa.asri_larisso.R;
import com.asa.asri_larisso.Response.BaseResponse;
import com.asa.asri_larisso.Session.Session;
import com.asa.asri_larisso.Table.Outlet;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_pilih_outlet_retail extends AppCompatActivity {

    ImageView back;
    ListView outlet;

    Api api;
    Session session;
    Call<BaseResponse<Outlet>> getOutlet;
    private ArrayList<String> kd_outlet = new ArrayList<>();
    private ArrayList<String> nama_outlet = new ArrayList<>();
    private ArrayList<String> keterangan = new ArrayList<>();
    private ArrayList<String> alamat = new ArrayList<>();
    private ArrayList<String> gambar = new ArrayList<>();

    AdapterOutlet adapterOutlet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_pilih_outlet_retail);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        outlet = findViewById(R.id.outlet);

        session = new Session(act_pilih_outlet_retail.this);
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());
    }

    public void dataOutlet() {
        getOutlet = api.getOutlet();
        getOutlet.enqueue(new Callback<BaseResponse<Outlet>>() {
            @Override
            public void onResponse(Call<BaseResponse<Outlet>> call, Response<BaseResponse<Outlet>> response) {
                if (response.isSuccessful()) {
                    kd_outlet.clear();
                    nama_outlet.clear();
                    keterangan.clear();
                    alamat.clear();
                    gambar.clear();

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        kd_outlet.add(response.body().getData().get(i).getKdOutlet());
                        nama_outlet.add(response.body().getData().get(i).getNamaOutlet());
                        keterangan.add(response.body().getData().get(i).getKeterangan());
                        alamat.add(response.body().getData().get(i).getAlamat());
                        gambar.add(response.body().getData().get(i).getGambarOutlet());
                    }

                    adapterOutlet = new AdapterOutlet(act_pilih_outlet_retail.this, kd_outlet, nama_outlet, keterangan, alamat, gambar);
                    adapterOutlet.notifyDataSetChanged();
                    outlet.setAdapter(adapterOutlet);
                } else {
                    Toasty.error(act_pilih_outlet_retail.this, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Outlet>> call, Throwable t) {
                Toasty.error(act_pilih_outlet_retail.this, "Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}