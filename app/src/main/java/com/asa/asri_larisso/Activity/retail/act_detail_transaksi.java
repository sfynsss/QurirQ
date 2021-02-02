package com.asa.asri_larisso.Activity.retail;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class  act_detail_transaksi extends AppCompatActivity {

    TextView no_ent, tgl_transaksi, waktu_transaksi, pengiriman, sts_byr, subtotal, potongan_voucher, ongkir, batalkan_pesanan;
    ListView list_barang;
    LinearLayout ly_ongkir, ly_potongan_voucher;
    Button lacak_pesanan;

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
        ly_ongkir = findViewById(R.id.ly_ongkir);
        ly_potongan_voucher = findViewById(R.id.ly_potongan_voucher);
        potongan_voucher = findViewById(R.id.potongan_voucher);
        ongkir = findViewById(R.id.ongkir);
        lacak_pesanan = findViewById(R.id.lacak_pesanan);
        batalkan_pesanan = findViewById(R.id.batalkan_pesanan);

        Locale localeID = new Locale("in", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        session = new Session(act_detail_transaksi.this);
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());

        no_ent.setText(getIntent().getStringExtra("no_ent"));
        tgl_transaksi.setText(getIntent().getStringExtra("tgl_transaksi"));
        waktu_transaksi.setText(getIntent().getStringExtra("waktu_transaksi"));
        pengiriman.setText(getIntent().getStringExtra("jns_pengiriman"));
        if (!TextUtils.isEmpty(getIntent().getStringExtra("ongkir"))) {
            ongkir.setText(formatRupiah.format(Double.parseDouble(getIntent().getStringExtra("ongkir"))).replace(",00",""));
        }

        System.out.println(getIntent().getStringExtra("sts_byr"));
        if (getIntent().getStringExtra("sts_byr").equals("0")) {
            sts_byr.setText("Belum Terbayar");
            sts_byr.setTextColor(Color.parseColor("#FFA52E"));
            batalkan_pesanan.setVisibility(View.VISIBLE);
        } else if (getIntent().getStringExtra("sts_byr").equals("1")) {
            sts_byr.setText("Lunas");
            sts_byr.setTextColor(Color.parseColor("#47D764"));
            batalkan_pesanan.setVisibility(View.GONE);
        } else if (getIntent().getStringExtra("sts_byr").equals("2")) {
            sts_byr.setText("Transaksi Dibatalkan");
            batalkan_pesanan.setVisibility(View.GONE);
        }
        subtotal.setText(formatRupiah.format(Double.parseDouble(getIntent().getStringExtra("total"))).replace(",00", ""));
        if (TextUtils.isEmpty(getIntent().getStringExtra("nilai_voucher"))) {
            ly_potongan_voucher.setVisibility(View.GONE);
            potongan_voucher.setText("0");
        } else {
            ly_potongan_voucher.setVisibility(View.VISIBLE);
            potongan_voucher.setText(getIntent().getStringExtra("nilai_voucher"));
        }

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

        lacak_pesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.success(getApplicationContext(), "Fitur segera hadir", Toast.LENGTH_SHORT).show();
            }
        });

        batalkan_pesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SweetAlertDialog pDialog = new SweetAlertDialog(act_detail_transaksi.this, SweetAlertDialog.WARNING_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Perhatian");
                pDialog.setContentText("Yakin ingin membatalkan pesanan ini?");
                pDialog.setConfirmText("Iya");
                pDialog.setCancelText("Tidak");
                pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                    }
                });
                pDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                });
                pDialog.show();
            }
        });

    }
}