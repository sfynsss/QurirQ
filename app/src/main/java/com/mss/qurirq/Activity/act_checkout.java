package com.mss.qurirq.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mss.qurirq.Api.Api;
import com.mss.qurirq.Api.RetrofitClient;
import com.mss.qurirq.R;
import com.mss.qurirq.Response.BaseResponse;
import com.mss.qurirq.Session.Session;
import com.mss.qurirq.Table.Jarak;
import com.mss.qurirq.Table.Pengiriman;
import com.mss.qurirq.Table.SettingPoint;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_checkout extends AppCompatActivity {

    private ArrayList<String> kd_brg = new ArrayList<>();
    private ArrayList<String> nm_brg = new ArrayList<>();
    private ArrayList<String> hrg_brg = new ArrayList<>();
    private ArrayList<String> hrg_asli = new ArrayList<>();
    private ArrayList<String> qty = new ArrayList<>();
    private ArrayList<String> berat = new ArrayList<>();
    private ArrayList<String> volume = new ArrayList<>();
    private ArrayList<String> gambar = new ArrayList<>();
    private ArrayList<String> sts_point = new ArrayList<>();
    double total = 0, netto = 0, ongkir_total = 0, potongan = 0, subtot_point = 0, tot_point = 0;
    boolean sts_alamat = false;
    NumberFormat formatRupiah;
    String no_ent, a = "";

    TextView ganti_alamat, total_belanja, ongkir, jumlah_total, harga_ongkir;
    TextView alamat_pengiriman, nama_penerima, no_penerima, nama_voucher, tx_voucher, hapus_voucher;
    ListView list_barang;
    Button btn_pengiriman, pilih_pembayaran;
    AdapterCheckout adapterCheckout;

    // Koordinat Lokasi Penerima
    String lat, lng, lat_resto, lng_resto;
    double jarak = 0;

    Session session;
    Api api;
    Call<Jarak> cekJarak;
    Call<BaseResponse> cekOngkirFood;
    Call<String> getNoEnt;
    Call<BaseResponse> inputPenjualan;

    static String before(String value, String a) {
        // Return substring containing all characters before a string.
        int posA = value.indexOf(a);
        if (posA == -1) {
            return "";
        }
        return value.substring(0, posA);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_checkout);

        kd_brg = (ArrayList<String>) getIntent().getSerializableExtra("kd_brg");
        nm_brg = (ArrayList<String>) getIntent().getSerializableExtra("nm_brg");
        hrg_brg = (ArrayList<String>) getIntent().getSerializableExtra("hrg_brg");
        hrg_asli = (ArrayList<String>) getIntent().getSerializableExtra("hrg_asli");
        qty = (ArrayList<String>) getIntent().getSerializableExtra("qty");
        gambar = (ArrayList<String>) getIntent().getSerializableExtra("gambar");
        sts_point = (ArrayList<String>) getIntent().getSerializableExtra("sts_point");

        total = Double.parseDouble(getIntent().getStringExtra("subtot"));
        Locale localeID = new Locale("in", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        session = new Session(act_checkout.this);
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ganti_alamat = findViewById(R.id.ganti_alamat);
        total_belanja = findViewById(R.id.total_belanja);
        ongkir = findViewById(R.id.ongkir);
        jumlah_total = findViewById(R.id.jumlah_total);
        pilih_pembayaran = findViewById(R.id.pilih_pembayaran);

        list_barang = findViewById(R.id.list_pesanan);

        alamat_pengiriman = findViewById(R.id.alamat_pengiriman);
        nama_penerima = findViewById(R.id.nama_penerima);
        no_penerima = findViewById(R.id.no_penerima);

        adapterCheckout = new AdapterCheckout(act_checkout.this, kd_brg, nm_brg, qty, hrg_asli, gambar);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.checkout_height) * kd_brg.size());
        list_barang.setFocusable(false);
        list_barang.setLayoutParams(params);
        list_barang.setAdapter(adapterCheckout);
        adapterCheckout.notifyDataSetChanged();

        lat_resto = getIntent().getStringExtra("lat");
        lng_resto = getIntent().getStringExtra("lng");

        total_belanja.setText("" + formatRupiah.format(total).replace(",00", ""));

        ganti_alamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(act_checkout.this, act_list_alamat.class);
                it.putExtra("pilih", "ya");
                startActivityForResult(it, 0);
            }
        });

        getNoEnt();

        pilih_pembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void getNoEnt() {
        getNoEnt = api.getNoEnt(session.getIdUser());
        getNoEnt.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    no_ent = response.body();
                    System.out.println(no_ent);
                } else {
                    Toasty.error(act_checkout.this, "Error, Data Tidak Ditemukan !!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toasty.error(act_checkout.this, "Data Tidak Ditemukan !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initInputPenjualan() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == 1) {
                alamat_pengiriman.setText(data.getStringExtra("alamat"));
                nama_penerima.setText(data.getStringExtra("nama_penerima"));
                no_penerima.setText(data.getStringExtra("no_telp_penerima"));
                lat = data.getStringExtra("latitude");
                lng = data.getStringExtra("longitude");
                sts_alamat = true;

                String origin = lat_resto+","+lng_resto;
                String destination = lat+","+lng;
                cekJarak = api.getJarak(origin, destination, "AIzaSyBhYpivDh3X593xIjPmfgqiMP3eB6KSbZM");
                cekJarak.enqueue(new Callback<Jarak>() {
                    @Override
                    public void onResponse(Call<Jarak> call, Response<Jarak> response) {
                        if (response.isSuccessful()) {
                            jarak = Double.parseDouble(before(response.body().getRows().get(0).getElements().get(0).getDistance().getText(), " "));
                            System.out.println(jarak);
                        } else {
                            Toasty.error(act_checkout.this, "Data Tidak Ditemukan !!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Jarak> call, Throwable t) {
                        Toasty.error(act_checkout.this, "Data Tidak Ditemukan !!!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }
    }

}