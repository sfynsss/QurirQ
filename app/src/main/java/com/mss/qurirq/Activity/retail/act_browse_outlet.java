package com.mss.qurirq.Activity.retail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mss.qurirq.Api.Api;
import com.mss.qurirq.Api.RetrofitClient;
import com.mss.qurirq.R;
import com.mss.qurirq.Response.BaseResponse;
import com.mss.qurirq.Session.Session;
import com.mss.qurirq.Table.Outlet;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_browse_outlet extends AppCompatActivity {

    ImageView back;
    ListView outlet;
    TextView nama_kategori;

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
        setContentView(R.layout.activity_act_browse_outlet);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        outlet = findViewById(R.id.outlet);
        nama_kategori = findViewById(R.id.nama_kategori);
        nama_kategori.setText(getIntent().getStringExtra("judul"));

        session = new Session(act_browse_outlet.this);
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());

        dataOutlet(getIntent().getStringExtra("id_kategori"));
        outlet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(act_browse_outlet.this, act_browse_barang.class);
                it.putExtra("id_outlet", kd_outlet.get(position));
                startActivity(it);
                finish();
            }
        });
    }

    public void dataOutlet(String id) {
        getOutlet = api.getOutlet(id);
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
                        kd_outlet.add(response.body().getData().get(i).getId().toString());
                        nama_outlet.add(response.body().getData().get(i).getNamaOutlet());
                        keterangan.add(response.body().getData().get(i).getKeterangan());
                        alamat.add(response.body().getData().get(i).getAlamat());
                        gambar.add(response.body().getData().get(i).getGambarOutlet());
                    }

                    adapterOutlet = new AdapterOutlet(act_browse_outlet.this, kd_outlet, nama_outlet, keterangan, alamat, gambar);
                    adapterOutlet.notifyDataSetChanged();
                    outlet.setAdapter(adapterOutlet);
                } else {
                    Toasty.error(act_browse_outlet.this, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Outlet>> call, Throwable t) {
                Toasty.error(act_browse_outlet.this, "Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}