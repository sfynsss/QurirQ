package com.asa.asri_larisso.Activity.retail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.asa.asri_larisso.Api.Api;
import com.asa.asri_larisso.Api.RetrofitClient;
import com.asa.asri_larisso.R;
import com.asa.asri_larisso.Response.BaseResponse;
import com.asa.asri_larisso.Session.Session;
import com.asa.asri_larisso.Table.Lacak;
import com.asa.asri_larisso.Table.MstJual;

import java.util.ArrayList;
import java.util.Locale;

public class act_order_track extends AppCompatActivity {

    TextView no_resi, jenis_pengiriman, waktu_transaksi;
    ListView list_lacak_pesanan;
    Call<BaseResponse<MstJual>> getNoResi;
    Call<BaseResponse<Lacak>> getLacakPengiriman;
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
        list_lacak_pesanan = findViewById(R.id.list_lacak_pesanan);

        Locale localeID = new Locale("in", "ID");
        session = new Session(act_order_track.this);
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());

        jenis_pengiriman.setText(getIntent().getStringExtra("jns_pengiriman"));
        waktu_transaksi.setText(getIntent().getStringExtra("tgl_transaksi") + " / " + getIntent().getStringExtra("waktu_transaksi"));

        getNoResi = api.getNoResi(getIntent().getStringExtra("no_ent"));
        getNoResi.enqueue(new Callback<BaseResponse<MstJual>>() {
            @Override
            public void onResponse(Call<BaseResponse<MstJual>> call, Response<BaseResponse<MstJual>> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        if (response.body().getData().get(i).getNoResi() == null){
                            no_resi.setText("Belum Diinput");
                        } else {
                            String resi = response.body().getData().get(i).getNoResi();
                            no_resi.setText(resi+"");
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

        getLacakPengiriman = api.getLacakPengiriman("000817954445","sicepat");
        //getLacakPengiriman = api.getLacakPengiriman(getIntent().getStringExtra("no_ent"), getIntent().getStringExtra("jns_pengiriman"));
        getLacakPengiriman.enqueue(new Callback<BaseResponse<Lacak>>() {
            @Override
            public void onResponse(Call<BaseResponse<Lacak>> call, Response<BaseResponse<Lacak>> response) {
                if (response.isSuccessful()){
                    manifest_desc.clear();
                    manifest_date.clear();
                    manifest_time.clear();
                    courier_name.clear();

                    manifest_desc.add("Halo");
                    manifest_date.add("Halo");
                    manifest_time.add("Halo");
                    courier_name.add("Halo");

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        System.out.println("test");
                        manifest_desc.add(response.body().getData().get(i).getManifestDescription());
                        manifest_date.add(response.body().getData().get(i).getManifestDate());
                        manifest_time.add(response.body().getData().get(i).getManifestTime());
                        courier_name.add(response.body().getData().get(i).getCityName());
                    }
                    adapterLacakPesanan = new AdapterLacakPesanan(
                            act_order_track.this,
                            manifest_desc,
                            manifest_date,
                            manifest_time,
                            courier_name
                    );
                    adapterLacakPesanan.notifyDataSetChanged();
                    list_lacak_pesanan.setAdapter(adapterLacakPesanan);
                } else {
                    Toasty.error(act_order_track.this, "Data Tidak Ditemukan !!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Lacak>> call, Throwable t) {
                Toasty.error(act_order_track.this, "Data Tidak Ditemukan !!!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}