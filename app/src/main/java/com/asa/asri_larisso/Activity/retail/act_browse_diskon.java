package com.asa.asri_larisso.Activity.retail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.asa.asri_larisso.Api.Api;
import com.asa.asri_larisso.Api.RetrofitClient;
import com.asa.asri_larisso.R;
import com.asa.asri_larisso.Response.BaseResponse;
import com.asa.asri_larisso.Session.Session;
import com.asa.asri_larisso.Table.Barang;
import com.asa.asri_larisso.Table.Promo;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_browse_diskon extends AppCompatActivity {

    ImageView back;
    TextView nama_kategori, nama_promo, tgl_mulai, tgl_akhir;
    LinearLayout periode_promo, promo_kosong;
    RecyclerView recyclerBarang;
    Api api;
    Session session;
    Call<BaseResponse<Barang>> getBarang;
    Call<BaseResponse<Promo>> getPromo;

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

    Boolean promo_aktif = false;
    Date c = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    String string_hari_ini = df.format(c).replace("-","");
    long int_hari_ini = Integer.parseInt(string_hari_ini);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_browse_diskon);

        back = findViewById(R.id.back);
        nama_kategori = findViewById(R.id.nama_kategori);
        recyclerBarang = findViewById(R.id.recycle_barang);
        nama_promo = findViewById(R.id.nama_promo);
        tgl_mulai = findViewById(R.id.tgl_mulai);
        tgl_akhir = findViewById(R.id.tgl_akhir);
        periode_promo = findViewById(R.id.periode_promo);
        promo_kosong = findViewById(R.id.promo_kosong);

        Locale localeID = new Locale("in", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        final String hari_ini = df.format(c.getTime());
        System.out.println("Hari ini adalah "+hari_ini);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        session = new Session(act_browse_diskon.this);
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());

        if (promo_aktif == false){
            nama_promo.setText("Promo Untuk Anda");
            periode_promo.setVisibility(View.VISIBLE);
            recyclerBarang.setVisibility(View.GONE);
            promo_kosong.setVisibility(View.VISIBLE);
        } else {
            periode_promo.setVisibility(View.VISIBLE);
            recyclerBarang.setVisibility(View.VISIBLE);
            promo_kosong.setVisibility(View.GONE);
            getPromo();
        }
    }

    public void getPromo(){
        getPromo = api.getPromo();
        getPromo.enqueue(new Callback<BaseResponse<Promo>>() {
            @Override
            public void onResponse(Call<BaseResponse<Promo>> call, Response<BaseResponse<Promo>> response) {
                if (response.isSuccessful()){
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        nama_promo.setText(response.body().getData().get(i).getNamaPromo());
                        tgl_akhir.setText(response.body().getData().get(i).getTglAkhir());

                        String s_tgl_mulai = response.body().getData().get(i).getTglMulai().replace("-","");
//                        String s_tgl_akhir = response.body().getData().get(i).getTglAkhir().replace("-","");
//                        long int_tgl_mulai = Integer.parseInt(s_tgl_mulai);
//                        long int_tgl_akhir = Integer.parseInt(s_tgl_akhir);
//
//                        if (int_hari_ini < int_tgl_mulai || int_hari_ini > int_tgl_akhir){
//                            promo_aktif = false;
//                        } else {
//                            promo_aktif = true;
//                        }

                        tgl_mulai.setText(s_tgl_mulai);

                    }
                } else {
                    Toasty.error(act_browse_diskon.this, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Promo>> call, Throwable t) {
                Toasty.error(act_browse_diskon.this, "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getData() {
        getBarang = api.getBarangDiskon(session.getKdOutlet()+"");
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

                    adapterBarang = new AdapterBarang(act_browse_diskon.this, act_browse_diskon.this,
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
                    recyclerBarang.setLayoutManager(new GridLayoutManager(act_browse_diskon.this, 2));
                    recyclerBarang.setAdapter(adapterBarang);

                } else {
                    Toasty.error(act_browse_diskon.this, "Data Tidak Ditemukan !!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Barang>> call, Throwable t) {
                Toasty.error(act_browse_diskon.this, "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}