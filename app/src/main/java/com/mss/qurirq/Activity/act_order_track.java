package com.mss.qurirq.Activity;

import androidx.appcompat.app.AppCompatActivity;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mss.qurirq.Api.Api;
import com.mss.qurirq.Api.RetrofitClient;
import com.mss.qurirq.R;
import com.mss.qurirq.Response.BaseResponse;
import com.mss.qurirq.Response.BaseResponseLacak;
import com.mss.qurirq.Session.Session;
import com.mss.qurirq.Table.MstJual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class act_order_track extends AppCompatActivity {

    TextView no_resi, jenis_pengiriman, waktu_transaksi, status;
    ListView list_lacak_pesanan;
    Call<BaseResponse<MstJual>> getNoResi;
    Call<BaseResponseLacak> getLacakPengiriman;
    Session session;
    Api api;

    private ArrayList<String> manifest_desc = new ArrayList<>();
    private ArrayList<String> manifest_date = new ArrayList<>();
    private ArrayList<String> manifest_time = new ArrayList<>();
    private ArrayList<String> courier_name = new ArrayList<>();

    AdapterLacakPesanan adapterLacakPesanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_order_track);

        jenis_pengiriman = findViewById(R.id.jenis_pengiriman);
        no_resi = findViewById(R.id.no_resi);
        waktu_transaksi = findViewById(R.id.waktu_transaksi);
        status = findViewById(R.id.status);
        list_lacak_pesanan = findViewById(R.id.list_lacak_pesanan);

        Locale localeID = new Locale("in", "ID");
        session = new Session(act_order_track.this);
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());

        getNoResi = api.getNoResi(getIntent().getStringExtra("no_ent"));
        getNoResi.enqueue(new Callback<BaseResponse<MstJual>>() {
            @Override
            public void onResponse(Call<BaseResponse<MstJual>> call, Response<BaseResponse<MstJual>> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        if (response.body().getData().get(i).getNoResi() == null) {
                            no_resi.setText("Belum Diinput");
                        } else {
                            final String resi = response.body().getData().get(i).getNoResi();
                            getLacakPengiriman = api.getLacakPengiriman(resi, getIntent().getStringExtra("jns_pengiriman"));
                            //getLacakPengiriman = api.getLacakPengiriman(getIntent().getStringExtra("no_ent"), getIntent().getStringExtra("jns_pengiriman"));
                            getLacakPengiriman.enqueue(new Callback<BaseResponseLacak>() {
                                @Override
                                public void onResponse(Call<BaseResponseLacak> call, Response<BaseResponseLacak> response) {
                                    if (response.isSuccessful()) {
                                        no_resi.setText(resi + "");
                                        waktu_transaksi.setText(response.body().getLacak().getTglKirim());
                                        jenis_pengiriman.setText(response.body().getLacak().getKodeKurir().toUpperCase());
                                        status.setText(response.body().getLacak().getStatus());

                                        manifest_desc.clear();
                                        manifest_date.clear();
                                        manifest_time.clear();
                                        courier_name.clear();

                                        for (int i = 0; i < response.body().getManifest().size(); i++) {
                                            System.out.println(response.body().getManifest().get(i).getManifestDescription());
                                            manifest_desc.add(response.body().getManifest().get(i).getManifestDescription());
                                            manifest_date.add(response.body().getManifest().get(i).getManifestDate());
                                            manifest_time.add(response.body().getManifest().get(i).getManifestTime());
                                            courier_name.add(response.body().getManifest().get(i).getCityName());
                                        }

                                        if (response.body().getLacak().getKodeKurir().equalsIgnoreCase("sicepat")) {
                                            Collections.reverse(manifest_desc);
                                            Collections.reverse(manifest_date);
                                            Collections.reverse(manifest_time);
                                        }
                                        adapterLacakPesanan = new AdapterLacakPesanan(
                                                act_order_track.this,
                                                manifest_desc,
                                                manifest_date,
                                                manifest_time
                                        );
                                        adapterLacakPesanan.notifyDataSetChanged();
                                        list_lacak_pesanan.setAdapter(adapterLacakPesanan);
                                    } else {
                                        Toasty.error(act_order_track.this, "Data Tidak Ditemukan !!!", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<BaseResponseLacak> call, Throwable t) {
                                    Toasty.error(act_order_track.this, "Data Tidak Ditemukan !!!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                } else {
                    Toasty.error(getApplicationContext(), "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<MstJual>> call, Throwable t) {
                Toasty.error(getApplicationContext(), "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
            }
        });

    }
}