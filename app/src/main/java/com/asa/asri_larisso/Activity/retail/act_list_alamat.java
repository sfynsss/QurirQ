package com.asa.asri_larisso.Activity.retail;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.asa.asri_larisso.Api.Api;
import com.asa.asri_larisso.Api.RetrofitClient;
import com.asa.asri_larisso.R;
import com.asa.asri_larisso.Response.BaseResponse;
import com.asa.asri_larisso.Session.Session;
import com.asa.asri_larisso.Table.Alamat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_list_alamat extends AppCompatActivity {

    FloatingActionButton btn_tambah;
    ListView list_alamat;

    Session session;
    Api api;
    Call<BaseResponse<Alamat>> getAlamat;

    ArrayList<String> kd_alamat = new ArrayList<>();
    ArrayList<String> nama_penerima = new ArrayList<>();
    ArrayList<String> no_telp_penerima = new ArrayList<>();
    ArrayList<String> alamat = new ArrayList<>();
    ArrayList<String> alamat_asli = new ArrayList<>();
    ArrayList<String> list_provinsi = new ArrayList<>();
    ArrayList<String> list_id_provinsi = new ArrayList<>();
    ArrayList<String> list_kota = new ArrayList<>();
    ArrayList<String> list_id_kota = new ArrayList<>();
    ArrayList<String> list_kecamatan = new ArrayList<>();
    ArrayList<String> list_id_kecamatan = new ArrayList<>();
    ArrayList<String> list_kode_pos = new ArrayList<>();
    ArrayList<String> latitude = new ArrayList<>();
    ArrayList<String> longitude = new ArrayList<>();

    AdapterAlamat adapterAlamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_list_alamat);

        btn_tambah = findViewById(R.id.btn_tambah);
        list_alamat = findViewById(R.id.list_alamat);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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
                    session.setAlamat(
                            nama_penerima.get(position),
                            list_provinsi.get(position),
                            list_kota.get(position),
                            list_kecamatan.get(position),
                            list_id_provinsi.get(position),
                            list_id_kota.get(position),
                            list_id_kecamatan.get(position),
                            alamat_asli.get(position),
                            list_kode_pos.get(position),
                            latitude.get(position),
                            longitude.get(position));
                    session.setNoTelp(no_telp_penerima.get(position));
                    Intent it = new Intent();
                    it.putExtra("alamat", alamat.get(position));
                    it.putExtra("nama_penerima", nama_penerima.get(position));
                    it.putExtra("no_telp_penerima", no_telp_penerima.get(position));
                    setResult(1, it);
                    finish();
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
                    kd_alamat.clear();
                    nama_penerima.clear();
                    no_telp_penerima.clear();
                    alamat.clear();
                    alamat_asli.clear();
                    list_provinsi.clear();
                    list_kota.clear();
                    list_kecamatan.clear();
                    list_id_provinsi.clear();
                    list_id_kota.clear();
                    list_id_kecamatan.clear();
                    list_kode_pos.clear();
                    latitude.clear();
                    longitude.clear();

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        kd_alamat.add(response.body().getData().get(i).getKdAlamat().toString());
                        nama_penerima.add(response.body().getData().get(i).getNamaPenerima());
                        no_telp_penerima.add(response.body().getData().get(i).getNoTelpPenerima());
                        alamat.add(response.body().getData().get(i).getAlamatLengkap()+", "+response.body().getData().get(i).getKecamatan()+", "+ response.body().getData().get(i).getKota()+", "+response.body().getData().get(i).getProvinsi());
                        alamat_asli.add(response.body().getData().get(i).getAlamatLengkap());
                        list_provinsi.add(response.body().getData().get(i).getProvinsi());
                        list_kota.add(response.body().getData().get(i).getKota());
                        list_kecamatan.add(response.body().getData().get(i).getKecamatan());
                        list_id_provinsi.add(response.body().getData().get(i).getKdProvinsi());
                        list_id_kota.add(response.body().getData().get(i).getKdKota());
                        list_id_kecamatan.add(response.body().getData().get(i).getKdKecamatan());
                        list_kode_pos.add(response.body().getData().get(i).getKodePos());
                        latitude.add(response.body().getData().get(i).getLatitude().toString());
                        longitude.add(response.body().getData().get(i).getLongitude().toString());
                    }

                    System.out.println(no_telp_penerima);
                    adapterAlamat = new AdapterAlamat(act_list_alamat.this, nama_penerima, alamat, new AdapterAlamat.OnEditLocationListener() {
                        @Override
                        public void onClickAdapter(int position) {
                            Intent it = new Intent(act_list_alamat.this, act_tambah_alamat.class);
                            it.putExtra("kd_alamat", kd_alamat.get(position));
                            it.putExtra("nama_penerima", nama_penerima.get(position));
                            it.putExtra("no_telp", no_telp_penerima.get(position));
                            it.putExtra("alamat", alamat_asli.get(position));
                            it.putExtra("kode_pos", list_kode_pos.get(position));
                            it.putExtra("sts_edit", "edited");
                            startActivityForResult(it, 0);
                        }
                    });
                    adapterAlamat.notifyDataSetChanged();
                    list_alamat.setAdapter(adapterAlamat);
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