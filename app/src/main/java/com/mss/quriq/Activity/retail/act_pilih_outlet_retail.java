package com.mss.quriq.Activity.retail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.mss.quriq.Api.Api;
import com.mss.quriq.Api.RetrofitClient;
import com.mss.quriq.R;
import com.mss.quriq.Response.BaseResponse;
import com.mss.quriq.Session.Session;
import com.mss.quriq.Table.Outlet;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_pilih_outlet_retail extends AppCompatActivity {

    ImageView back;
    ListView outlet;

    Integer sts_aktif = 0;

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

        dataOutlet();
        outlet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                session.setOutlet(kd_outlet.get(position), nama_outlet.get(position), gambar.get(position), true);
                startActivity(new Intent(act_pilih_outlet_retail.this, act_home.class));
                finish();
            }
        });

//        if (session.getUserActivation() == false || getIntent().getStringExtra("sts_aktif_otp").equals(1)) {
//            Intent it = new Intent(act_pilih_outlet_retail.this, act_home_retail.class);
//            it.putExtra("sts_aktif_otp", 1);
//        } else {
//            Intent it = new Intent(act_pilih_outlet_retail.this, act_home_retail.class);
//            it.putExtra("sts_aktif_otp", 0);
//        }

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