package com.mss.qurirq.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mss.qurirq.Api.Api;
import com.mss.qurirq.Api.RetrofitClient;
import com.mss.qurirq.R;
import com.mss.qurirq.Response.BaseResponse;
import com.mss.qurirq.Session.Session;
import com.mss.qurirq.Table.Alamat;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_list_alamat extends AppCompatActivity {

    Button btn_tambah;
    ListView list_alamat;
    RelativeLayout alamat_ada, alamat_kosong;

    Session session;
    Api api;
    Call<BaseResponse<Alamat>> getAlamat;

    ArrayList<String> kd_alamat = new ArrayList<>();
    ArrayList<String> nama_penerima = new ArrayList<>();
    ArrayList<String> no_telp_penerima = new ArrayList<>();
    ArrayList<String> alamat = new ArrayList<>();
    ArrayList<String> alamat_asli = new ArrayList<>();
    ArrayList<String> latitude = new ArrayList<>();
    ArrayList<String> longitude = new ArrayList<>();

    AdapterAlamat adapterAlamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_list_alamat);

        btn_tambah = findViewById(R.id.btn_tambah);
        list_alamat = findViewById(R.id.list_alamat);
        alamat_ada = findViewById(R.id.alamat_ada);
        alamat_kosong = findViewById(R.id.alamat_kosong);

        session = new Session(act_list_alamat.this);
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());

        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(act_list_alamat.this, act_tambah_alamat.class), 0);
            }
        });

        dataAlamat();

        if (!TextUtils.isEmpty(getIntent().getStringExtra("pilih"))) {
            list_alamat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent it = new Intent();
                    it.putExtra("alamat", alamat.get(position));
                    it.putExtra("nama_penerima", nama_penerima.get(position));
                    it.putExtra("no_telp_penerima", no_telp_penerima.get(position));
                    it.putExtra("latitude", latitude.get(position));
                    it.putExtra("longitude", longitude.get(position));
                    setResult(1, it);
                    finish();
                    Toasty.success(act_list_alamat.this, "Alamat ke " + alamat_asli.get(position) + " dipilih", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void dataAlamat(){
        getAlamat = api.getAlamat(session.getIdUser());
        getAlamat.enqueue(new Callback<BaseResponse<Alamat>>() {
            @Override
            public void onResponse(Call<BaseResponse<Alamat>> call, Response<BaseResponse<Alamat>> response) {
                if (response.isSuccessful()) {

                    if (response.body().getData().isEmpty()) {
                        alamat_kosong.setVisibility(View.VISIBLE);
                        alamat_ada.setVisibility(View.GONE);
                    } else {
                        alamat_kosong.setVisibility(View.GONE);
                        alamat_ada.setVisibility(View.VISIBLE);

                        kd_alamat.clear();
                        nama_penerima.clear();
                        no_telp_penerima.clear();
                        alamat.clear();
                        alamat_asli.clear();
                        latitude.clear();
                        longitude.clear();

                        for (int i = 0; i < response.body().getData().size(); i++) {
                            kd_alamat.add(response.body().getData().get(i).getKdAlamat().toString());
                            nama_penerima.add(response.body().getData().get(i).getNamaPenerima());
                            no_telp_penerima.add(response.body().getData().get(i).getNoTelpPenerima());
                            alamat.add(response.body().getData().get(i).getAlamatLengkap());
                            alamat_asli.add(response.body().getData().get(i).getAlamatLengkap());
                            latitude.add(response.body().getData().get(i).getLatitude().toString());
                            longitude.add(response.body().getData().get(i).getLongitude().toString());
                        }

                        adapterAlamat = new AdapterAlamat(act_list_alamat.this, nama_penerima, alamat, new AdapterAlamat.OnEditLocationListener() {
                            @Override
                            public void onClickAdapter(int position) {
                                Intent it = new Intent(act_list_alamat.this, act_tambah_alamat.class);
                                it.putExtra("kd_alamat", kd_alamat.get(position));
                                it.putExtra("nama_penerima", nama_penerima.get(position));
                                it.putExtra("no_telp", no_telp_penerima.get(position));
                                it.putExtra("alamat", alamat_asli.get(position));
                                it.putExtra("sts_edit", "edited");
                                startActivityForResult(it, 0);
                            }
                        });
                        adapterAlamat.notifyDataSetChanged();
                        list_alamat.setAdapter(adapterAlamat);
                    }

                } else {
                    Toasty.error(act_list_alamat.this, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Alamat>> call, Throwable t) {
                Toasty.error(act_list_alamat.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 1) {
            dataAlamat();
        }
    }
}