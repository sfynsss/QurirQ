package com.asa.asri_larisso.Activity.retail;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asa.asri_larisso.Api.Api;
import com.asa.asri_larisso.Api.RetrofitClient;
import com.asa.asri_larisso.R;
import com.asa.asri_larisso.Response.BaseResponse;
import com.asa.asri_larisso.Session.Session;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;

import java.text.NumberFormat;
import java.util.Locale;
import android.text.InputFilter;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_detail_barang_retail extends AppCompatActivity {

    ImageView gambar, back;
    TextView nama_barang, kategori, harga, jml, berat_brg, volume_brg;
    Button btn_min, btn_plus, ke_cart, ke_wishlist;
    NumberFormat formatRupiah;
    int i = 1;
    double berat_total = 0;
    String kd_brg = "", nm_brg = "", satuan = "", harga_jl = "", qty = "", gbr = "", kat = "", berat = "", volume="";

    Api api;
    Session session;
    Call<BaseResponse> inputToCart;
    Call<BaseResponse> inputToWishlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_detail_barang_retail);

        back = findViewById(R.id.back);
        gambar = findViewById(R.id.gambar);
        nama_barang = findViewById(R.id.nama_barang);
        kategori = findViewById(R.id.kategori_barang);
        harga = findViewById(R.id.harga_barang);
        jml = findViewById(R.id.jml);
        jml.setText(i+"");
        jml.setFilters( new InputFilter[]{ new QtyMinMax( "1" , "100" )}) ;
        berat_brg = findViewById(R.id.berat_brg);
        volume_brg = findViewById(R.id.volume);
        btn_min = findViewById(R.id.min);
        btn_plus = findViewById(R.id.plus);
        ke_cart = findViewById(R.id.ke_cart);
        ke_wishlist = findViewById(R.id.ke_wishlist);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final ProgressDialog pd = new ProgressDialog(act_detail_barang_retail.this);

        Locale localeID = new Locale("in", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        session = new Session(act_detail_barang_retail.this);
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());

        RequestOptions requestOptions = new RequestOptions().centerInside().placeholder(R.drawable.ic_hourglass_empty_24).error(R.drawable.ic_highlight_off_24);
        requestOptions.signature(
                new ObjectKey(String.valueOf(System.currentTimeMillis())));
        Glide.with(act_detail_barang_retail.this)
                .setDefaultRequestOptions(requestOptions)
//                .load("http://192.168.1.16:8000/storage/" + getIntent().getStringExtra("gambar") + "")
                .load("http://"+session.getBaseUrl()+"/storage/" + getIntent().getStringExtra("gambar") + "")
                .into(gambar);

        nama_barang.setText(getIntent().getStringExtra("nm_brg"));
        kategori.setText(getIntent().getStringExtra("kat_brg"));
        harga.setText(formatRupiah.format(Double.parseDouble(getIntent().getStringExtra("harga_jl"))).replace(",00", ""));
        berat_brg.setText(getIntent().getStringExtra("berat"));
        volume_brg.setText(getIntent().getStringExtra("volume"));
        kd_brg = getIntent().getStringExtra("kd_brg");
        nm_brg = nama_barang.getText().toString();
        satuan = getIntent().getStringExtra("satuan");
        harga_jl = getIntent().getStringExtra("harga_jl").replace(",00", "");
        qty = jml.getText().toString();
        gbr = getIntent().getStringExtra("gambar");
        kat = getIntent().getStringExtra("kat_brg");
        berat = getIntent().getStringExtra("berat");
        volume = getIntent().getStringExtra("volume");

        btn_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i > 1) {
                    i -= 1;
                    jml.setText(i + "");

                } else {
                    i = 1;
                    jml.setText(i + "");
                }
            }
        });

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i += 1;
                jml.setText(i + "");
            }
        });

        ke_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kd_brg = getIntent().getStringExtra("kd_brg");
                nm_brg = nama_barang.getText().toString();
                satuan = getIntent().getStringExtra("satuan");
                harga_jl = getIntent().getStringExtra("harga_jl");
                qty = jml.getText().toString();
                berat = getIntent().getStringExtra("berat");
                volume = getIntent().getStringExtra("volume");
                gbr = getIntent().getStringExtra("gambar");
                kat = getIntent().getStringExtra("kat_brg");

                inputToCart = api.inputToCart(session.getIdUser(), kd_brg, nm_brg, satuan, harga_jl, qty, berat, volume, gbr, kat, session.getKdOutlet());
                inputToCart.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful()) {
                            Toasty.success(act_detail_barang_retail.this, "Success " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } else {
                            Toasty.error(act_detail_barang_retail.this, "Error, Input Data Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        Toasty.error(act_detail_barang_retail.this, "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        ke_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pd.setTitle("Mohon Menunggu");
                pd.setMessage("Barang sedang ditambahkan");
                pd.setCancelable(false);
                pd.show();

                kd_brg = getIntent().getStringExtra("kd_brg");
                nm_brg = nama_barang.getText().toString();
                satuan = getIntent().getStringExtra("satuan");
                harga_jl = getIntent().getStringExtra("harga_jl");
                gbr = getIntent().getStringExtra("gambar");
                kat = getIntent().getStringExtra("kat_brg");
                berat = getIntent().getStringExtra("berat");
                volume = getIntent().getStringExtra("volume");
                ke_wishlist.setBackgroundResource(R.drawable.rt_ic_fav_on);
                inputToWishlist = api.inputToWishlist(session.getIdUser(), kd_brg, nm_brg, satuan, harga_jl, berat, volume, gbr, kat);
                inputToWishlist.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.code() == 200) {
                                pd.hide();
                                Toasty.success(act_detail_barang_retail.this, "Success " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                //onBackPressed();
                            } else if (response.code() == 201) {
                                pd.hide();
                                Toasty.success(act_detail_barang_retail.this, "Success " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                //onBackPressed();
                            }
                        } else {
                            pd.hide();
                            Toasty.error(act_detail_barang_retail.this, "Error, Input Data Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        pd.hide();
                        Toasty.error(act_detail_barang_retail.this, "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}