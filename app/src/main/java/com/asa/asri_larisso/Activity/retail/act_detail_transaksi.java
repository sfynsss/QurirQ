package com.asa.asri_larisso.Activity.retail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.asa.asri_larisso.Api.Api;
import com.asa.asri_larisso.Api.RetrofitClient;
import com.asa.asri_larisso.R;
import com.asa.asri_larisso.Response.BaseResponse;
import com.asa.asri_larisso.Session.Session;
import com.asa.asri_larisso.Table.DetJual;
import com.asa.asri_larisso.Table.MstJual;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_detail_transaksi extends AppCompatActivity {

    TextView no_ent, tgl_transaksi, waktu_transaksi, pengiriman, sts_byr, subtotal;
    ListView list_barang;

    NumberFormat formatRupiah;
    Session session;
    Api api;
    Call<BaseResponse<DetJual>> getDetailTransaksi;
    AdapterDetailTransaksi adapterDetailTransaksi;

    ArrayList<String> nm_brg = new ArrayList<>();
    ArrayList<Integer> qty = new ArrayList<>();
    ArrayList<Integer> sub = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_detail_transaksi);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        no_ent = findViewById(R.id.no_ent);
        tgl_transaksi = findViewById(R.id.tgl_transaksi);
        waktu_transaksi = findViewById(R.id.waktu_transaksi);
        pengiriman = findViewById(R.id.pengiriman);
        sts_byr = findViewById(R.id.sts_byr);
        subtotal = findViewById(R.id.subtotal);
        list_barang = findViewById(R.id.list_barang);

        Locale localeID = new Locale("in", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        session = new Session(act_detail_transaksi.this);
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());

        no_ent.setText(getIntent().getStringExtra("no_ent"));
        tgl_transaksi.setText(getIntent().getStringExtra("tgl_transaksi"));
        waktu_transaksi.setText(getIntent().getStringExtra("waktu_transaksi"));
        pengiriman.setText(getIntent().getStringExtra("jns_pengiriman"));
        System.out.println(getIntent().getStringExtra("sts_byr"));
        if (getIntent().getStringExtra("sts_byr").equals("0")) {
            sts_byr.setText("Belum Terbayar");
        } else if (getIntent().getStringExtra("sts_byr").equals("1")) {
            sts_byr.setText("Lunas");
        }
        subtotal.setText(formatRupiah.format(Double.parseDouble(getIntent().getStringExtra("total"))).replace(",00", ""));

        getDetailTransaksi = api.getDetailTransaksi(getIntent().getStringExtra("no_ent"));
        getDetailTransaksi.enqueue(new Callback<BaseResponse<DetJual>>() {
            @Override
            public void onResponse(Call<BaseResponse<DetJual>> call, Response<BaseResponse<DetJual>> response) {
                if (response.isSuccessful()) {
                    nm_brg.clear();
                    qty.clear();
                    sub.clear();

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        nm_brg.add(response.body().getData().get(i).getNmBrg());
                        qty.add(response.body().getData().get(i).getJumlah());
                        sub.add(response.body().getData().get(i).getSubTotal());
                    }

                    adapterDetailTransaksi = new AdapterDetailTransaksi(act_detail_transaksi.this, nm_brg, qty, sub);
                    adapterDetailTransaksi.notifyDataSetChanged();
                    list_barang.setAdapter(adapterDetailTransaksi);
                } else {
                    Toasty.error(act_detail_transaksi.this, "Data Tidak Ditemukan !!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<DetJual>> call, Throwable t) {
                Toasty.error(act_detail_transaksi.this, t.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });
    }
}