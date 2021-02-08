package com.asa.asri_larisso.Activity.retail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.asa.asri_larisso.Api.Api;
import com.asa.asri_larisso.Api.RetrofitClient;
import com.asa.asri_larisso.R;
import com.asa.asri_larisso.Response.BaseResponse;
import com.asa.asri_larisso.Session.Session;
import com.asa.asri_larisso.Table.Barang;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_browse_barang extends AppCompatActivity {

    ImageView back;
    TextView nama_kategori, cari_brg;
    Button filter_hrg_rendah, filter_hrg_tinggi, filter_hrg_diskon, btn_search;
    RecyclerView recyclerBarang;
    ProgressBar pb_loading;
    Api api;
    Session session;
    Call<BaseResponse<Barang>> getBarang;
    Call<BaseResponse<Barang>> getBarangHargaRendah;
    Call<BaseResponse<Barang>> getBarangHargaTinggi;
    Call<BaseResponse<Barang>> getBarangHargaDiskon;
    Call<BaseResponse<Barang>> getBarangByNameByCategory;

    ArrayList<String> kd_brg = new ArrayList<>();
    ArrayList<String> nm_brg = new ArrayList<>();
    ArrayList<String> kat_brg = new ArrayList<>();
    ArrayList<String> hrg_brg = new ArrayList<>();
    ArrayList<String> harga_asli = new ArrayList<>();
    ArrayList<String> harga_jl2 = new ArrayList<>();
    ArrayList<String> harga_jl3 = new ArrayList<>();
    ArrayList<String> harga_jl4 = new ArrayList<>();
    ArrayList<String> qty_min2 = new ArrayList<>();
    ArrayList<String> qty_min3 = new ArrayList<>();
    ArrayList<String> qty_min4 = new ArrayList<>();
    ArrayList<String> satuan = new ArrayList<>();
    ArrayList<String> gambar = new ArrayList<>();
    ArrayList<String> disc = new ArrayList<>();
    ArrayList<String> harga_disc = new ArrayList<>();

    AdapterBarang adapterBarang;
    NumberFormat formatRupiah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_browse_barang);

        back = findViewById(R.id.back);
        nama_kategori = findViewById(R.id.nama_kategori);
        recyclerBarang = findViewById(R.id.recycle_barang);
        cari_brg = findViewById(R.id.cari_brg);
        filter_hrg_rendah = findViewById(R.id.filter_hrg_rendah);
        filter_hrg_tinggi = findViewById(R.id.filter_hrg_tinggi);
        filter_hrg_diskon = findViewById(R.id.filter_hrg_diskon);
        btn_search = findViewById(R.id.btn_search);
        pb_loading = findViewById(R.id.pb_loading);

        nama_kategori.setText(getIntent().getStringExtra("judul"));
        Locale localeID = new Locale("in", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        session = new Session(act_browse_barang.this);
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());
        getBarang = api.getBarang(getIntent().getStringExtra("kd_kategori"), session.getKdOutlet()+"");
        tampilBarang();

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pb_loading.setVisibility(View.VISIBLE);
                getBarangByNameByCategory = api.getBarangByNameByCategory(cari_brg.getText().toString(), getIntent().getStringExtra("kd_kategori"), session.getKdOutlet()+"");
                tampilBarangByName();
            }
        });

        filter_hrg_rendah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBarangHargaRendah = api.getBarangHargaRendah(getIntent().getStringExtra("kd_kategori"), session.getKdOutlet()+"");
                tampilBarangRendah();
                filter_hrg_rendah.setBackgroundResource(R.drawable.rt_filter_bt_on);
                filter_hrg_rendah.setTextColor(Color.parseColor("#FFFFFF"));
                filter_hrg_diskon.setBackgroundResource(R.drawable.rt_filter_bt_off);
                filter_hrg_diskon.setTextColor(Color.parseColor("#FFA722"));
                filter_hrg_tinggi.setBackgroundResource(R.drawable.rt_filter_bt_off);
                filter_hrg_tinggi.setTextColor(Color.parseColor("#FFA722"));
                Toasty.success(act_browse_barang.this, "Disortir berdasarkan harga terendah ", Toast.LENGTH_SHORT).show();
            }
        });

        filter_hrg_tinggi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBarangHargaTinggi = api.getBarangHargaTinggi(getIntent().getStringExtra("kd_kategori"), session.getKdOutlet()+"");
                tampilBarangTinggi();
                filter_hrg_tinggi.setBackgroundResource(R.drawable.rt_filter_bt_on);
                filter_hrg_tinggi.setTextColor(Color.parseColor("#FFFFFF"));
                filter_hrg_rendah.setBackgroundResource(R.drawable.rt_filter_bt_off);
                filter_hrg_rendah.setTextColor(Color.parseColor("#FFA722"));
                filter_hrg_diskon.setBackgroundResource(R.drawable.rt_filter_bt_off);
                filter_hrg_diskon.setTextColor(Color.parseColor("#FFA722"));
                Toasty.success(act_browse_barang.this, "Disortir berdasarkan harga tertinggi ", Toast.LENGTH_SHORT).show();
            }
        });

        filter_hrg_diskon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBarangHargaDiskon = api.getBarangHargaDiskon(getIntent().getStringExtra("kd_kategori"), session.getKdOutlet()+"");
                tampilBarangDiskon();
                filter_hrg_diskon.setBackgroundResource(R.drawable.rt_filter_bt_on);
                filter_hrg_diskon.setTextColor(Color.parseColor("#FFFFFF"));
                filter_hrg_tinggi.setBackgroundResource(R.drawable.rt_filter_bt_off);
                filter_hrg_tinggi.setTextColor(Color.parseColor("#FFA722"));
                filter_hrg_rendah.setBackgroundResource(R.drawable.rt_filter_bt_off);
                filter_hrg_rendah.setTextColor(Color.parseColor("#FFA722"));
                Toasty.success(act_browse_barang.this, "Disortir berdasarkan promo ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void tampilBarang(){
        getBarang.enqueue(new Callback<BaseResponse<Barang>>() {
            @Override
            public void onResponse(Call<BaseResponse<Barang>> call, Response<BaseResponse<Barang>> response) {
                if (response.isSuccessful()) {
                    kd_brg.clear();
                    nm_brg.clear();
                    kat_brg.clear();
                    hrg_brg.clear();
                    harga_asli.clear();
                    harga_jl2.clear();
                    harga_jl3.clear();
                    harga_jl4.clear();
                    qty_min2.clear();
                    qty_min3.clear();
                    qty_min4.clear();
                    satuan.clear();
                    gambar.clear();
                    disc.clear();
                    harga_disc.clear();

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        kd_brg.add(response.body().getData().get(i).getKdBrg());
                        nm_brg.add(response.body().getData().get(i).getNmBrg());
                        kat_brg.add(getIntent().getStringExtra("judul"));
                        hrg_brg.add(formatRupiah.format(response.body().getData().get(i).getHargaJl()));
                        harga_asli.add(response.body().getData().get(i).getHargaJl().toString());
                        harga_jl2.add(response.body().getData().get(i).getHargaJl2().toString());
                        harga_jl3.add(response.body().getData().get(i).getHargaJl3().toString());
                        harga_jl4.add(response.body().getData().get(i).getHargaJl4().toString());
                        qty_min2.add(response.body().getData().get(i).getQtyMin2().toString());
                        qty_min3.add(response.body().getData().get(i).getQtyMin3().toString());
                        qty_min4.add(response.body().getData().get(i).getQtyMin4().toString());
                        satuan. add(response.body().getData().get(i).getSatuan1());
                        gambar.add(response.body().getData().get(i).getGambar());
                        disc.add(response.body().getData().get(i).getDisc().toString());
                        harga_disc.add(response.body().getData().get(i).getHargaDisc().toString());
                    }

                    adapterBarang = new AdapterBarang(act_browse_barang.this, act_browse_barang.this,
                            kd_brg,
                            gambar,
                            nm_brg,
                            kat_brg,
                            harga_asli,
                            hrg_brg,
                            harga_jl2,
                            harga_jl3,
                            harga_jl4,
                            qty_min2,
                            qty_min3,
                            qty_min4,
                            satuan,
                            disc,
                            harga_disc);
                    recyclerBarang.setLayoutManager(new GridLayoutManager(act_browse_barang.this, 2));
                    recyclerBarang.setAdapter(adapterBarang);

                } else {
                    Toasty.error(act_browse_barang.this, "Data Tidak Ditemukan !!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Barang>> call, Throwable t) {
                Toasty.error(act_browse_barang.this, "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void tampilBarangByName(){
        getBarangByNameByCategory.enqueue(new Callback<BaseResponse<Barang>>() {
            @Override
            public void onResponse(Call<BaseResponse<Barang>> call, Response<BaseResponse<Barang>> response) {
                if (response.isSuccessful()) {
                    kd_brg.clear();
                    nm_brg.clear();
                    kat_brg.clear();
                    hrg_brg.clear();
                    harga_asli.clear();
                    harga_jl2.clear();
                    harga_jl3.clear();
                    harga_jl4.clear();
                    qty_min2.clear();
                    qty_min3.clear();
                    qty_min4.clear();
                    satuan.clear();
                    gambar.clear();
                    disc.clear();
                    harga_disc.clear();

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        kd_brg.add(response.body().getData().get(i).getKdBrg());
                        nm_brg.add(response.body().getData().get(i).getNmBrg());
                        kat_brg.add(getIntent().getStringExtra("judul"));
                        hrg_brg.add(formatRupiah.format(response.body().getData().get(i).getHargaJl()));
                        harga_asli.add(response.body().getData().get(i).getHargaJl().toString());
                        harga_jl2.add(response.body().getData().get(i).getHargaJl2().toString());
                        harga_jl3.add(response.body().getData().get(i).getHargaJl3().toString());
                        harga_jl4.add(response.body().getData().get(i).getHargaJl4().toString());
                        qty_min2.add(response.body().getData().get(i).getQtyMin2().toString());
                        qty_min3.add(response.body().getData().get(i).getQtyMin3().toString());
                        qty_min4.add(response.body().getData().get(i).getQtyMin4().toString());
                        satuan. add(response.body().getData().get(i).getSatuan1());
                        gambar.add(response.body().getData().get(i).getGambar());
                        disc.add(response.body().getData().get(i).getDisc().toString());
                        harga_disc.add(response.body().getData().get(i).getHargaDisc().toString());
                    }

                    adapterBarang = new AdapterBarang(act_browse_barang.this, act_browse_barang.this,
                            kd_brg,
                            gambar,
                            nm_brg,
                            kat_brg,
                            harga_asli,
                            hrg_brg,
                            harga_jl2,
                            harga_jl3,
                            harga_jl4,
                            qty_min2,
                            qty_min3,
                            qty_min4,
                            satuan,
                            disc,
                            harga_disc);
                    recyclerBarang.setLayoutManager(new GridLayoutManager(act_browse_barang.this, 2));
                    recyclerBarang.setAdapter(adapterBarang);

                } else {
                    Toasty.error(act_browse_barang.this, "Data Tidak Ditemukan !!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Barang>> call, Throwable t) {
                Toasty.error(act_browse_barang.this, "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void tampilBarangRendah(){
        getBarangHargaRendah.enqueue(new Callback<BaseResponse<Barang>>() {
            @Override
            public void onResponse(Call<BaseResponse<Barang>> call, Response<BaseResponse<Barang>> response) {
                if (response.isSuccessful()) {
                    kd_brg.clear();
                    nm_brg.clear();
                    kat_brg.clear();
                    hrg_brg.clear();
                    harga_asli.clear();
                    harga_jl2.clear();
                    harga_jl3.clear();
                    harga_jl4.clear();
                    qty_min2.clear();
                    qty_min3.clear();
                    qty_min4.clear();
                    satuan.clear();
                    gambar.clear();
                    disc.clear();
                    harga_disc.clear();

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        kd_brg.add(response.body().getData().get(i).getKdBrg());
                        nm_brg.add(response.body().getData().get(i).getNmBrg());
                        kat_brg.add(getIntent().getStringExtra("judul"));
                        hrg_brg.add(formatRupiah.format(response.body().getData().get(i).getHargaJl()));
                        harga_asli.add(response.body().getData().get(i).getHargaJl().toString());
                        harga_jl2.add(response.body().getData().get(i).getHargaJl2().toString());
                        harga_jl3.add(response.body().getData().get(i).getHargaJl3().toString());
                        harga_jl4.add(response.body().getData().get(i).getHargaJl4().toString());
                        qty_min2.add(response.body().getData().get(i).getQtyMin2().toString());
                        qty_min3.add(response.body().getData().get(i).getQtyMin3().toString());
                        qty_min4.add(response.body().getData().get(i).getQtyMin4().toString());
                        satuan. add(response.body().getData().get(i).getSatuan1());
                        gambar.add(response.body().getData().get(i).getGambar());
                        disc.add(response.body().getData().get(i).getDisc().toString());
                        harga_disc.add(response.body().getData().get(i).getHargaDisc().toString());
                    }

                    adapterBarang = new AdapterBarang(act_browse_barang.this, act_browse_barang.this,
                            kd_brg,
                            gambar,
                            nm_brg,
                            kat_brg,
                            harga_asli,
                            hrg_brg,
                            harga_jl2,
                            harga_jl3,
                            harga_jl4,
                            qty_min2,
                            qty_min3,
                            qty_min4,
                            satuan,
                            disc,
                            harga_disc);
                    recyclerBarang.setLayoutManager(new GridLayoutManager(act_browse_barang.this, 2));
                    recyclerBarang.setAdapter(adapterBarang);

                } else {
                    Toasty.error(act_browse_barang.this, "Data Tidak Ditemukan !!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Barang>> call, Throwable t) {
                Toasty.error(act_browse_barang.this, "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void tampilBarangTinggi(){
        getBarangHargaTinggi.enqueue(new Callback<BaseResponse<Barang>>() {
            @Override
            public void onResponse(Call<BaseResponse<Barang>> call, Response<BaseResponse<Barang>> response) {
                if (response.isSuccessful()) {
                    kd_brg.clear();
                    nm_brg.clear();
                    kat_brg.clear();
                    hrg_brg.clear();
                    harga_asli.clear();
                    harga_jl2.clear();
                    harga_jl3.clear();
                    harga_jl4.clear();
                    qty_min2.clear();
                    qty_min3.clear();
                    qty_min4.clear();
                    satuan.clear();
                    gambar.clear();
                    disc.clear();
                    harga_disc.clear();

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        kd_brg.add(response.body().getData().get(i).getKdBrg());
                        nm_brg.add(response.body().getData().get(i).getNmBrg());
                        kat_brg.add(getIntent().getStringExtra("judul"));
                        hrg_brg.add(formatRupiah.format(response.body().getData().get(i).getHargaJl()));
                        harga_asli.add(response.body().getData().get(i).getHargaJl().toString());
                        harga_jl2.add(response.body().getData().get(i).getHargaJl2().toString());
                        harga_jl3.add(response.body().getData().get(i).getHargaJl3().toString());
                        harga_jl4.add(response.body().getData().get(i).getHargaJl4().toString());
                        qty_min2.add(response.body().getData().get(i).getQtyMin2().toString());
                        qty_min3.add(response.body().getData().get(i).getQtyMin3().toString());
                        qty_min4.add(response.body().getData().get(i).getQtyMin4().toString());
                        satuan. add(response.body().getData().get(i).getSatuan1());
                        gambar.add(response.body().getData().get(i).getGambar());
                        disc.add(response.body().getData().get(i).getDisc().toString());
                        harga_disc.add(response.body().getData().get(i).getHargaDisc().toString());
                    }

                    adapterBarang = new AdapterBarang(act_browse_barang.this, act_browse_barang.this,
                            kd_brg,
                            gambar,
                            nm_brg,
                            kat_brg,
                            harga_asli,
                            hrg_brg,
                            harga_jl2,
                            harga_jl3,
                            harga_jl4,
                            qty_min2,
                            qty_min3,
                            qty_min4,
                            satuan,
                            disc,
                            harga_disc);
                    recyclerBarang.setLayoutManager(new GridLayoutManager(act_browse_barang.this, 2));
                    recyclerBarang.setAdapter(adapterBarang);

                } else {
                    Toasty.error(act_browse_barang.this, "Data Tidak Ditemukan !!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Barang>> call, Throwable t) {
                Toasty.error(act_browse_barang.this, "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void tampilBarangDiskon(){
        getBarangHargaDiskon.enqueue(new Callback<BaseResponse<Barang>>() {
            @Override
            public void onResponse(Call<BaseResponse<Barang>> call, Response<BaseResponse<Barang>> response) {
                if (response.isSuccessful()) {
                    kd_brg.clear();
                    nm_brg.clear();
                    kat_brg.clear();
                    hrg_brg.clear();
                    harga_asli.clear();
                    harga_jl2.clear();
                    harga_jl3.clear();
                    harga_jl4.clear();
                    qty_min2.clear();
                    qty_min3.clear();
                    qty_min4.clear();
                    satuan.clear();
                    gambar.clear();
                    disc.clear();
                    harga_disc.clear();

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        kd_brg.add(response.body().getData().get(i).getKdBrg());
                        nm_brg.add(response.body().getData().get(i).getNmBrg());
                        kat_brg.add(getIntent().getStringExtra("judul"));
                        hrg_brg.add(formatRupiah.format(response.body().getData().get(i).getHargaJl()));
                        harga_asli.add(response.body().getData().get(i).getHargaJl().toString());
                        harga_jl2.add(response.body().getData().get(i).getHargaJl2().toString());
                        harga_jl3.add(response.body().getData().get(i).getHargaJl3().toString());
                        harga_jl4.add(response.body().getData().get(i).getHargaJl4().toString());
                        qty_min2.add(response.body().getData().get(i).getQtyMin2().toString());
                        qty_min3.add(response.body().getData().get(i).getQtyMin3().toString());
                        qty_min4.add(response.body().getData().get(i).getQtyMin4().toString());
                        satuan. add(response.body().getData().get(i).getSatuan1());
                        gambar.add(response.body().getData().get(i).getGambar());
                        disc.add(response.body().getData().get(i).getDisc().toString());
                        harga_disc.add(response.body().getData().get(i).getHargaDisc().toString());
                    }

                    adapterBarang = new AdapterBarang(act_browse_barang.this, act_browse_barang.this,
                            kd_brg,
                            gambar,
                            nm_brg,
                            kat_brg,
                            harga_asli,
                            hrg_brg,
                            harga_jl2,
                            harga_jl3,
                            harga_jl4,
                            qty_min2,
                            qty_min3,
                            qty_min4,
                            satuan,
                            disc,
                            harga_disc);
                    recyclerBarang.setLayoutManager(new GridLayoutManager(act_browse_barang.this, 2));
                    recyclerBarang.setAdapter(adapterBarang);

                } else {
                    Toasty.error(act_browse_barang.this, "Data Tidak Ditemukan !!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Barang>> call, Throwable t) {
                Toasty.error(act_browse_barang.this, "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}