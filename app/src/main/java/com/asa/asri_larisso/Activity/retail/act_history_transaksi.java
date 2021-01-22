package com.asa.asri_larisso.Activity.retail;

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
import com.asa.asri_larisso.Table.MstJual;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_history_transaksi extends AppCompatActivity {

    ListView list_transaksi;
    Session session;
    Api api;
    Call<BaseResponse<MstJual>> getDataTransaksi;

    ArrayList<String> no_ent = new ArrayList<>();
    ArrayList<Integer> jml_item = new ArrayList<>();
    ArrayList<String> tgl_transaksi = new ArrayList<>();
    ArrayList<String> waktu_transaksi = new ArrayList<>();
    ArrayList<String> jns_pengiriman = new ArrayList<>();
    ArrayList<String> disc_value = new ArrayList<>();
    ArrayList<Integer> ongkir = new ArrayList<>();
    ArrayList<Integer> subtot = new ArrayList<>();
    ArrayList<Integer> sts_byr = new ArrayList<>();

    AdapterTransaksi adapterTransaksi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_history_transaksi);

        list_transaksi = findViewById(R.id.list_transaksi);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        session = new Session(act_history_transaksi.this);
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());

        getDataTransaksi = api.getDataTransaksi(session.getIdUser());
        getDataTransaksi.enqueue(new Callback<BaseResponse<MstJual>>() {
            @Override
            public void onResponse(Call<BaseResponse<MstJual>> call, Response<BaseResponse<MstJual>> response) {
                if (response.isSuccessful()) {
                    no_ent.clear();
                    jml_item.clear();
                    tgl_transaksi.clear();
                    waktu_transaksi.clear();
                    subtot.clear();
                    ongkir.clear();
                    disc_value.clear();
                    sts_byr.clear();
                    jns_pengiriman.clear();

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        no_ent.add(response.body().getData().get(i).getNoEnt());
                        jml_item.add(response.body().getData().get(i).getJumlah());
                        tgl_transaksi.add(TextUtils.substring(response.body().getData().get(i).getTanggal(), 0, 10));
                        waktu_transaksi.add(TextUtils.substring(response.body().getData().get(i).getTanggal(), 11, 16));
                        if (!TextUtils.isEmpty(response.body().getData().get(i).getDiscValue())) {
                            disc_value.add(response.body().getData().get(i).getDiscValue());
                        } else {
                            disc_value.add("");
                        }
                        ongkir.add(response.body().getData().get(i).getOngkir());
                        sts_byr.add(response.body().getData().get(i).getStsByr());
                        subtot.add(response.body().getData().get(i).getTotal());
                        jns_pengiriman.add(response.body().getData().get(i).getJnsPengiriman());
                    }

                    System.out.println(waktu_transaksi);
                    adapterTransaksi = new AdapterTransaksi(act_history_transaksi.this, no_ent, jml_item, tgl_transaksi, subtot);
                    adapterTransaksi.notifyDataSetChanged();
                    list_transaksi.setAdapter(adapterTransaksi);
                } else {
                    Toasty.error(act_history_transaksi.this, "Anda belum bertransaksi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<MstJual>> call, Throwable t) {
                Toasty.error(act_history_transaksi.this, t.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });

        list_transaksi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(act_history_transaksi.this, act_detail_transaksi.class);
                it.putExtra("no_ent", no_ent.get(position));
                it.putExtra("tgl_transaksi", tgl_transaksi.get(position));
                it.putExtra("waktu_transaksi", waktu_transaksi.get(position));
                it.putExtra("total", subtot.get(position)+"");
                it.putExtra("sts_byr", sts_byr.get(position)+"");
                it.putExtra("jns_pengiriman", jns_pengiriman.get(position));
                startActivity(it);
            }
        });
    }
}