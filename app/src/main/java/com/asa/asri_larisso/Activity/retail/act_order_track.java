package com.asa.asri_larisso.Activity.retail;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.asa.asri_larisso.Api.Api;
import com.asa.asri_larisso.Api.RetrofitClient;
import com.asa.asri_larisso.R;
import com.asa.asri_larisso.Response.BaseResponse;
import com.asa.asri_larisso.Session.Session;
import com.asa.asri_larisso.Table.MstJual;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.Locale;

public class act_order_track extends AppCompatActivity {

    TextView no_resi, jenis_pengiriman, waktu_transaksi;
    Call<BaseResponse<MstJual>> getNoResi;
    Session session;
    Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_order_track);

        jenis_pengiriman = findViewById(R.id.jenis_pengiriman);
        no_resi = findViewById(R.id.no_resi);
        waktu_transaksi = findViewById(R.id.waktu_transaksi);

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

            }
        });

    }
}