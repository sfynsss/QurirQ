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
    boolean sts_kurir = false;
    NumberFormat formatRupiah;
    String no_ent, a = "";

    TextView ganti_alamat, total_belanja, ongkir, jumlah_total, harga_ongkir, potongan_voucher, v_total_berat, v_total_volume;
    TextView alamat_pengiriman, nama_penerima, no_penerima, nama_voucher, tx_voucher, hapus_voucher;
    ImageView nama_kurir;
    LinearLayout ly_gunakan_voucher, ly_potongan_voucher;
    ListView list_barang;
    Button btn_pengiriman, pilih_pembayaran;
    Spinner servis;
    AdapterCheckout adapterCheckout;
    TextView pilih_voucher;

    int ketentuan_point = 0, nilai_point = 0;

    ArrayList<Integer> kurir = new ArrayList<>();
    ArrayList<String> kurir_name = new ArrayList<>();
    ArrayList<String> service = new ArrayList<>();
    ArrayList<String> costs = new ArrayList<>();
    ArrayList<String> hrg_ongkir = new ArrayList<>();
    ListView list_pengiriman;
    String tmp_nm_voucher = "", tmp_kd_voucher = "";
    double nilai_voucher = 0;

    // Koordinat Lokasi Penerima
    double initialLat = 0;
    double initialLong = 0;

    // Koordinat Lokasi Larisso
    double finalLat = -8.3495079;
    double finalLong = 113.6051873;

    Session session;
    Api api;
    Call<BaseResponse<Pengiriman>> cekOngkir;
    Call<BaseResponse<SettingPoint>> getSettingPoint;
    Call<BaseResponse> cekOngkirCod;
    Call<String> getNoEnt;
    Call<BaseResponse> inputPenjualan;

    String no_va = "", payment_type = "", payment_bank = "";

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

        list_barang = findViewById(R.id.list_pesanan);
//        nama_kurir = findViewById(R.id.nama_kurir);
//        servis = findViewById(R.id.service);
//        harga_ongkir = findViewById(R.id.harga_ongkir);
//        btn_pengiriman = findViewById(R.id.btn_pengiriman);
        pilih_pembayaran = findViewById(R.id.pilih_pembayaran);
        pilih_voucher = findViewById(R.id.pilih_voucher);
        nama_voucher = findViewById(R.id.nama_voucher);
        tx_voucher = findViewById(R.id.tx_voucher);
        ly_gunakan_voucher = findViewById(R.id.ly_gunakan_voucher);
        hapus_voucher = findViewById(R.id.hapus_voucher);
        potongan_voucher = findViewById(R.id.potongan_voucher);
        ly_potongan_voucher = findViewById(R.id.ly_potongan_voucher);

        alamat_pengiriman = findViewById(R.id.alamat_pengiriman);
        nama_penerima = findViewById(R.id.nama_penerima);
        no_penerima = findViewById(R.id.no_penerima);

        alamat_pengiriman.setText(session.getAlamat() + ", " + session.getKecamatan() + ", " + session.getKota() + ", " + session.getProvinsi());
        nama_penerima.setText(session.getNamaPenerima());
        no_penerima.setText(session.getNoTelp());

        adapterCheckout = new AdapterCheckout(act_checkout.this, kd_brg, nm_brg, qty, hrg_asli, gambar);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.checkout_height) * kd_brg.size());
        list_barang.setFocusable(false);
        list_barang.setLayoutParams(params);
        list_barang.setAdapter(adapterCheckout);
        adapterCheckout.notifyDataSetChanged();

        total_belanja.setText("" + formatRupiah.format(total).replace(",00", ""));

        ganti_alamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(act_checkout.this, act_list_alamat.class);
                it.putExtra("pilih", "ya");
                startActivityForResult(it, 0);
            }
        });

        pilih_voucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(act_checkout.this, act_voucher.class);
                intent.putExtra("sts", "checkout");
                startActivityForResult(intent, 0);
            }
        });


        hapus_voucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tx_voucher.setVisibility(View.VISIBLE);
                ly_gunakan_voucher.setVisibility(View.GONE);
                ly_potongan_voucher.setVisibility(View.GONE);
                tmp_nm_voucher = "";
                tmp_kd_voucher = "";
                nilai_voucher = 0;
                nama_voucher.setText("");
                if (sts_kurir == false) {
                    harga_ongkir.setText("");
                    ongkir.setText(formatRupiah.format(0).replace(",00", ""));
                    if (nilai_voucher == 0) {
                        jumlah_total.setText(formatRupiah.format(0 + total).replace(",00", ""));
                    } else {
                        jumlah_total.setText(formatRupiah.format(0 + total - nilai_voucher).replace(",00", ""));
                    }
                    netto = total - nilai_voucher;
                } else {
                    harga_ongkir.setText(formatRupiah.format(ongkir_total).replace(",00", ""));
                    ongkir.setText(formatRupiah.format(ongkir_total).replace(",00", ""));
                    if (nilai_voucher == 0) {
                        jumlah_total.setText(formatRupiah.format(ongkir_total + total).replace(",00", ""));
                    } else {
                        jumlah_total.setText(formatRupiah.format(ongkir_total + total - nilai_voucher).replace(",00", ""));
                    }
                    netto = ongkir_total + total - nilai_voucher;
                }
            }
        });


//        servis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                harga_ongkir.setText(formatRupiah.format(Double.parseDouble(costs.get(position))).replace(",00", ""));
//                ongkir.setText(formatRupiah.format(Double.parseDouble(costs.get(position))).replace(",00", ""));
//                if (nilai_voucher == 0) {
//                    jumlah_total.setText(formatRupiah.format(Double.parseDouble(costs.get(position)) + total).replace(",00", ""));
//                } else {
//                    if (nilai_voucher <= (Double.parseDouble(costs.get(position)) + total)) {
//                        jumlah_total.setText(formatRupiah.format(Double.parseDouble(costs.get(position)) + total - nilai_voucher).replace(",00", ""));
//                    } else {
//                        jumlah_total.setText(formatRupiah.format(0).replace(",00", ""));
//                    }
//                }
//                if (nilai_voucher <= (Double.parseDouble(costs.get(position)) + total)) {
//                    netto = Double.parseDouble(costs.get(position)) + total - nilai_voucher;
//                } else {
//                    netto = 0;
//                }
//                ongkir_total = Double.parseDouble(costs.get(position).replace(",00", ""));
//                sts_kurir = true;
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

//        btn_pengiriman.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pilihPengiriman();
//            }
//        });

        getNoEnt();
        settingPoint();

        pilih_pembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void getOngkir(String asal, String destinasi, String berat, String kurir) {
        cekOngkir = api.cekOngkir(asal, destinasi, berat, kurir);
        cekOngkir.enqueue(new Callback<BaseResponse<Pengiriman>>() {
            @Override
            public void onResponse(Call<BaseResponse<Pengiriman>> call, Response<BaseResponse<Pengiriman>> response) {
                if (response.isSuccessful()) {
                    service.clear();
                    costs.clear();

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        System.out.println(i);
                        service.add(response.body().getData().get(i).getDescription() + " (est " + response.body().getData().get(i).getCost().get(0).getEtd() + " hari)");
                        costs.add(response.body().getData().get(i).getCost().get(0).getValue().toString());
                    }

                    System.out.println(service);
                    System.out.println(costs);
                    //Ini buat ngisi Spinner
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(act_checkout.this, R.layout.spinner_pengiriman, service);
                    arrayAdapter.setDropDownViewResource(R.layout.spinner_pengiriman);
                    servis.setAdapter(arrayAdapter);
                    //End isi spinner
                } else {
                    Toasty.error(act_checkout.this, "Data Tidak Ditemukan !!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Pengiriman>> call, Throwable t) {
                Toasty.error(act_checkout.this, "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getOngkirCod(final String jarak) {
        cekOngkirCod = api.cekOngkirCod(jarak);
        cekOngkirCod.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    service.clear();
                    costs.clear();

                    service.add("Next Day (" + jarak + " km)");
                    costs.add(response.body().getMessage());

                    System.out.println(service);
                    System.out.println(costs);
                    //Ini buat ngisi Spinner
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(act_checkout.this, R.layout.spinner_pengiriman, service);
                    arrayAdapter.setDropDownViewResource(R.layout.spinner_pengiriman);
                    servis.setAdapter(arrayAdapter);
                    //End isi spinner
                } else {
                    Toasty.error(act_checkout.this, "Data Tidak Ditemukan !!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toasty.error(act_checkout.this, "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void settingPoint() {
        getSettingPoint = api.getSettingPoint();
        getSettingPoint.enqueue(new Callback<BaseResponse<SettingPoint>>() {
            @Override
            public void onResponse(Call<BaseResponse<SettingPoint>> call, Response<BaseResponse<SettingPoint>> response) {
                if (response.isSuccessful()) {
                    ketentuan_point = response.body().getData().get(0).getKetentuan();
                    nilai_point = response.body().getData().get(0).getNilaiPoint();
                } else {
                    Toasty.error(act_checkout.this, "Setting Point Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<SettingPoint>> call, Throwable t) {
                Toasty.error(act_checkout.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initInputPenjualan(String sts_bayar, String transaction_id, final String va, final String payment_bank, final String payment_type, final String sts) {
        String kode_barang = TextUtils.join(";", kd_brg);
        String nama_barang = TextUtils.join(";", nm_brg);
        String harga_barang = TextUtils.join(";", hrg_asli);
        String quantity = TextUtils.join(";", qty);

        System.out.println(no_va + " | " + payment_bank + " | " + payment_type);

        System.out.println(kode_barang);
        System.out.println(nama_barang);
        System.out.println(harga_barang);
        System.out.println(quantity);
        subtot_point = 0;
        for (int i = 0; i < sts_point.size(); i++) {
            if (!sts_point.get(i).equals("1")) {
                subtot_point += (Double.parseDouble(hrg_asli.get(i)) * Double.parseDouble(qty.get(i)));
            }
        }
        System.out.println(subtot_point + "subtot point");
        System.out.println(ketentuan_point + "ketentuan point");
        tot_point = 0;
        if (TextUtils.isEmpty(tmp_kd_voucher)) {
            tot_point = (int) subtot_point / ketentuan_point;
        } else {
            tot_point = 0;
        }

        System.out.println(tot_point + "tot point");
        inputPenjualan = api.inputPenjualan(no_ent, session.getIdUser(), nama_penerima.getText().toString(), alamat_pengiriman.getText().toString(),
                no_penerima.getText().toString(), total + "", "", nilai_voucher, tmp_kd_voucher, "", netto + "",
                ongkir_total + "", a + "", "", kode_barang, nama_barang, harga_barang, quantity, "pcs", "RETAIL", sts_bayar, transaction_id,
                va, payment_bank, payment_type, tot_point + "");
        inputPenjualan.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    if (sts.equals("sukses")) {
                        if (a.equals("pickup")) {
                            Toasty.success(act_checkout.this, "Pesanan Berhasil Ditempatkan", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(act_checkout.this, act_home.class));
                            finish();
                        } else {
                            startActivity(new Intent(act_checkout.this, act_home.class));
                            finish();
                        }
                    } else if (sts.equals("pending")) {
                        Intent it = new Intent(act_checkout.this, act_status_pembayaran.class);
                        it.putExtra("payment_type", payment_type + "");
                        it.putExtra("payment_bank", payment_bank + "");
                        it.putExtra("va", va + "");
                        it.putExtra("total", netto + "");
                        startActivity(it);
                        finish();
                    }
                } else {
                    Toasty.error(act_checkout.this, "Error, Input Data Gagal !!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toasty.error(act_checkout.this, t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == 1) {
                alamat_pengiriman.setText(data.getStringExtra("alamat"));
                nama_penerima.setText(data.getStringExtra("nama_penerima"));
                no_penerima.setText(data.getStringExtra("no_telp_penerima"));
//                nama_kurir.setImageResource(0);
//                service.clear();
//                costs.clear();
//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(act_checkout.this, R.layout.spinner_pengiriman, service);
//                arrayAdapter.setDropDownViewResource(R.layout.spinner_pengiriman);
//                servis.setAdapter(arrayAdapter);
//                harga_ongkir.setText("");
//                ongkir.setText("");
//                jumlah_total.setText(formatRupiah.format(Double.parseDouble("0") + total).replace(",00", ""));
//                netto = Double.parseDouble("0") + total;
//                ongkir_total = Double.parseDouble("0");
//                sts_kurir = false;
            } else if (resultCode == 2) {
                tx_voucher.setVisibility(View.GONE);
                ly_gunakan_voucher.setVisibility(View.VISIBLE);
                ly_potongan_voucher.setVisibility(View.VISIBLE);
                tmp_nm_voucher = data.getStringExtra("nama_voucher");
                tmp_kd_voucher = data.getStringExtra("kd_voucher");
                nilai_voucher = Double.parseDouble(data.getStringExtra("nilai_voucher"));
                nama_voucher.setText(tmp_nm_voucher);
                potongan_voucher.setText(formatRupiah.format(nilai_voucher).replace(",00", ""));
                if (sts_kurir == false) {
                    harga_ongkir.setText("");
                    ongkir.setText(formatRupiah.format(0).replace(",00", ""));
                    if (nilai_voucher == 0) {
                        jumlah_total.setText(formatRupiah.format(0 + total).replace(",00", ""));
                    } else {
                        if (nilai_voucher <= total) {
                            jumlah_total.setText(formatRupiah.format(0 + total - nilai_voucher).replace(",00", ""));
                        } else if (nilai_voucher > total) {
                            jumlah_total.setText(formatRupiah.format(0).replace(",00", ""));
                        }
                    }
                    if (nilai_voucher <= total) {
                        netto = total - nilai_voucher;
                    } else {
                        netto = 0;
                    }
                } else {
                    harga_ongkir.setText(formatRupiah.format(ongkir_total).replace(",00", ""));
                    ongkir.setText(formatRupiah.format(ongkir_total).replace(",00", ""));
                    if (nilai_voucher == 0) {
                        jumlah_total.setText(formatRupiah.format(ongkir_total + total).replace(",00", ""));
                    } else {
                        if (nilai_voucher <= (total + ongkir_total)) {
                            jumlah_total.setText(formatRupiah.format(ongkir_total + total - nilai_voucher).replace(",00", ""));
                        } else if (nilai_voucher > (total + ongkir_total)) {
                            jumlah_total.setText(formatRupiah.format(0).replace(",00", ""));
                        }
                    }
                    if (nilai_voucher <= (total + ongkir_total)) {
                        netto = ongkir_total + total - nilai_voucher;
                    } else {
                        netto = 0;
                    }
                }
            }

        }

    }

    public double CalculationByDistance(double initialLat, double initialLong, double finalLat, double finalLong) {
        int R = 6371; // km (Earth radius)
        double dLat = toRadians(finalLat - initialLat);
        double dLon = toRadians(finalLong - initialLong);
        initialLat = toRadians(initialLat);
        finalLat = toRadians(finalLat);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(initialLat) * Math.cos(finalLat);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    public double toRadians(double deg) {
        return deg * (Math.PI / 180);
    }

}