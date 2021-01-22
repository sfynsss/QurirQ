package com.asa.asri_larisso.Activity.retail;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.asa.asri_larisso.Api.Api;
import com.asa.asri_larisso.Api.RetrofitClient;
import com.asa.asri_larisso.R;
import com.asa.asri_larisso.Response.BaseResponse;
import com.asa.asri_larisso.Session.Session;
import com.asa.asri_larisso.Table.Pengiriman;
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
import com.midtrans.sdk.corekit.core.MidtransSDK;
import com.midtrans.sdk.corekit.core.TransactionRequest;
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme;
import com.midtrans.sdk.corekit.models.BankType;
import com.midtrans.sdk.corekit.models.BillingAddress;
import com.midtrans.sdk.corekit.models.CustomerDetails;
import com.midtrans.sdk.corekit.models.ExpiryModel;
import com.midtrans.sdk.corekit.models.ItemDetails;
import com.midtrans.sdk.corekit.models.ShippingAddress;
import com.midtrans.sdk.corekit.models.snap.CreditCard;
import com.midtrans.sdk.corekit.models.snap.TransactionResult;
import com.midtrans.sdk.uikit.SdkUIFlowBuilder;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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
    private ArrayList<String> gambar = new ArrayList<>();
    double total = 0, netto = 0, ongkir_total = 0;
    boolean sts_kurir = false;
    NumberFormat formatRupiah;
    String no_ent, a = "";

    TextView ganti_alamat, total_belanja, ongkir, jumlah_total, harga_ongkir;
    TextView alamat_pengiriman, nama_penerima, no_penerima, nama_voucher;
    ImageView nama_kurir;
    ListView list_barang;
    Button btn_pengiriman, pilih_pembayaran;
    Spinner servis;
    AdapterCheckout adapterCheckout;
    TextView pilih_voucher;

    ArrayList<Integer> kurir = new ArrayList<>();
    ArrayList<String> service = new ArrayList<>();
    ArrayList<String> costs = new ArrayList<>();
    ArrayList<String> hrg_ongkir = new ArrayList<>();
    ListView list_pengiriman;
    String tmp_nm_voucher, tmp_kd_voucher;
    double nilai_voucher = 0;

    // Koordinat Lokasi Penerima
    double initialLat = 0;
    double initialLong = 0;

    // Koordinat Lokasi Larisso
    double finalLat = -8.3495079;
    double finalLong =  113.6051873;

    Session session;
    Api api;
    Call<BaseResponse<Pengiriman>> cekOngkir;
    Call<BaseResponse> cekOngkirCod;
    Call<String> getNoEnt;
    Call<BaseResponse> inputPenjualan;

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
        nama_kurir = findViewById(R.id.nama_kurir);
        servis = findViewById(R.id.service);
        harga_ongkir = findViewById(R.id.harga_ongkir);
        btn_pengiriman = findViewById(R.id.btn_pengiriman);
        pilih_pembayaran = findViewById(R.id.pilih_pembayaran);
        pilih_voucher = findViewById(R.id.pilih_voucher);
        nama_voucher = findViewById(R.id.nama_voucher);

        alamat_pengiriman = findViewById(R.id.alamat_pengiriman);
        nama_penerima = findViewById(R.id.nama_penerima);
        no_penerima = findViewById(R.id.no_penerima);

        alamat_pengiriman.setText(session.getAlamat() + ", " + session.getKecamatan() + ", " + session.getKota() + ", " + session.getProvinsi());
        nama_penerima.setText(session.getNamaPenerima());
        no_penerima.setText(session.getNoTelp());

        adapterCheckout = new AdapterCheckout(act_checkout.this, kd_brg, nm_brg, qty, hrg_asli, gambar);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.checkout_height) * kd_brg.size());
        list_barang.setLayoutParams(params);
        list_barang.setAdapter(adapterCheckout);
        adapterCheckout.notifyDataSetChanged();

        total_belanja.setText("" + formatRupiah.format(total));

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

        servis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                harga_ongkir.setText(formatRupiah.format(Double.parseDouble(costs.get(position))));
                ongkir.setText(formatRupiah.format(Double.parseDouble(costs.get(position))));
                jumlah_total.setText(formatRupiah.format(Double.parseDouble(costs.get(position)) + total));
                netto = Double.parseDouble(costs.get(position)) + total;
                ongkir_total = Double.parseDouble(costs.get(position));
                sts_kurir = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_pengiriman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihPengiriman();
            }
        });

        getNoEnt();

        pilih_pembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sts_kurir == false) {
                    final SweetAlertDialog dialog = new SweetAlertDialog(act_checkout.this, SweetAlertDialog.WARNING_TYPE);
                    dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    dialog.setTitleText("Silahkan pilih jasa pengiriman terlebih dauhulu !!!");
                    dialog.setCancelable(false);
                    dialog.show();
                } else {
                    initMidtransSdk();
                    MidtransSDK.getInstance().setTransactionRequest(initTransactionRequest());
                    MidtransSDK.getInstance().startPaymentUiFlow(act_checkout.this);
                }
            }
        });
    }

    private TransactionRequest initTransactionRequest() {
        // Create new Transaction Request

        //set customer details
        TransactionRequest transactionRequest = new TransactionRequest(no_ent, (long) netto);
        transactionRequest.setCustomerDetails(initCustomerDetails());

        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setAddress(session.getAlamat());
        shippingAddress.setCity(session.getKota());
        shippingAddress.setPhone(session.getNoTelp());
        shippingAddress.setFirstName(session.getNamaPenerima());
        shippingAddress.setLastName("");
        shippingAddress.setPostalCode(session.getKodePos());
        shippingAddress.setCountryCode("id");

        BillingAddress billingAddress = new BillingAddress();
        billingAddress.setAddress(session.getAlamat());
        billingAddress.setCity(session.getKota());
        billingAddress.setPhone(session.getNoTelp());
        billingAddress.setFirstName(session.getNamaPenerima());
        billingAddress.setLastName("");
        billingAddress.setPostalCode(session.getKodePos());
        billingAddress.setCountryCode("id");

        ArrayList<BillingAddress> billingAddressArrayList = new ArrayList<>();
        billingAddressArrayList.add(billingAddress);
        ArrayList<ShippingAddress> shippingAddressArrayList = new ArrayList<>();
        shippingAddressArrayList.add(shippingAddress);

        transactionRequest.setBillingAddressArrayList(billingAddressArrayList);
        transactionRequest.setShippingAddressArrayList(shippingAddressArrayList);

        // set item details
        ArrayList<ItemDetails> itemDetailsArrayList = new ArrayList<>();
        ItemDetails itemDetails1 = new ItemDetails("ongkir", (int) ongkir_total, 1, "ongkir");
        itemDetailsArrayList.add(itemDetails1);
        for (int i = 0; i < kd_brg.size(); i++) {
            ItemDetails itemDetails = new ItemDetails(kd_brg.get(i), Integer.parseInt(hrg_asli.get(i)), Integer.parseInt(qty.get(i)), nm_brg.get(i));
            itemDetailsArrayList.add(itemDetails);
        }

        // Add item details into item detail list.

        transactionRequest.setItemDetails(itemDetailsArrayList);

        ExpiryModel expiryModel = new ExpiryModel();
        // set start time
        long timeInMili = System.currentTimeMillis();
// format the time
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
// format the time as a string
        String nowAsISO = df.format(new Date(timeInMili));
// set the formatted time to expiry model
        expiryModel.setStartTime(nowAsISO);
        expiryModel.setUnit(ExpiryModel.UNIT_DAY);
        expiryModel.setDuration(1);

        transactionRequest.setExpiry(expiryModel);

        CreditCard creditCardOptions = new CreditCard();
// Set to true if you want to save card to Snap
        creditCardOptions.setSaveCard(false);
// Set to true to save card token as `one click` token
        creditCardOptions.setSecure(false);
// Set bank name when using MIGS channel
        creditCardOptions.setBank(BankType.BCA);
// Set MIGS channel (ONLY for BCA, BRI and Maybank Acquiring bank)
        creditCardOptions.setChannel(CreditCard.MIGS);
// Set Credit Card Options
        transactionRequest.setCreditCard(creditCardOptions);
// Set transaction request into SDK instance
        MidtransSDK.getInstance().setTransactionRequest(transactionRequest);

        return transactionRequest;
    }

    private CustomerDetails initCustomerDetails() {

        //define customer detail (mandatory for coreflow)
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setAddress(session.getAlamat()+"");
        shippingAddress.setCity(session.getKota()+"");
        shippingAddress.setPhone(session.getNoTelp()+"");
        shippingAddress.setFirstName(session.getNamaPenerima()+"");
        shippingAddress.setPostalCode(session.getKodePos()+"");
        shippingAddress.setCountryCode("id");

        BillingAddress billingAddress = new BillingAddress();
        billingAddress.setAddress(session.getAlamat()+"");
        billingAddress.setCity(session.getKota()+"");
        billingAddress.setPhone(session.getNoTelp()+"");
        billingAddress.setFirstName(session.getNamaPenerima()+"");
        billingAddress.setPostalCode(session.getKodePos()+"");
        billingAddress.setCountryCode("id");

        CustomerDetails mCustomerDetails = new CustomerDetails(session.getNamaPenerima()+"","", session.getEmail()+"",session.getNoTelp()+"");
        mCustomerDetails.setFirstName(session.getNamaPenerima()+"");
        mCustomerDetails.setLastName("");
        mCustomerDetails.setEmail(session.getEmail()+"");
        mCustomerDetails.setPhone(session.getNoTelp()+"");
//
        System.out.println(session.getNamaPenerima());
        System.out.println(session.getEmail());
        System.out.println(session.getNoTelp());
        System.out.println(session.getAlamat());
        System.out.println(session.getKota());
        System.out.println(session.getKodePos());

        mCustomerDetails.setBillingAddress(billingAddress);
        mCustomerDetails.setShippingAddress(shippingAddress);
        return mCustomerDetails;
    }

    private void initMidtransSdk() {
        String client_key = SdkConfig.MERCHANT_CLIENT_KEY;
        String base_url = SdkConfig.MERCHANT_BASE_CHECKOUT_URL;

        SdkUIFlowBuilder.init()
                .setClientKey(client_key) // client_key is mandatory
                .setContext(act_checkout.this) // context is mandatory
                .setTransactionFinishedCallback(new TransactionFinishedCallback() {
                    @Override
                    public void onTransactionFinished(TransactionResult result) {
                        if (result.getResponse() != null) {
                            switch (result.getStatus()) {
                                case TransactionResult.STATUS_SUCCESS:
                                    Toast.makeText(act_checkout.this, "Transaction Finished. ID: " + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
                                    initInputPenjualan("1", result.getResponse().getTransactionId());
                                    break;
                                case TransactionResult.STATUS_PENDING:
                                    Toast.makeText(act_checkout.this, "Transaction Pending", Toast.LENGTH_LONG).show();
//                                    System.out.println("VA"+result.getResponse().getAccountNumbers().get(0).getAccountNumber());
//                                    System.out.println("Payment type"+result.getResponse().getPaymentType());
//                                    System.out.println("Status code"+result.getResponse().getStatusCode());
//                                    System.out.println("Transaction ID"+result.getResponse().getTransactionId());
//                                    System.out.println("Bank"+result.getResponse().getAccountNumbers().get(0).getBank());
//                                    System.out.println("Expire"+result.getResponse().getPermataExpiration());
                                    initInputPenjualan("0", result.getResponse().getTransactionId());

//                                    Intent it = new Intent(act_checkout.this, act_status_pembayaran.class);
//                                    it.putExtra("VA", result.getResponse().getAccountNumbers().get(0).getAccountNumber());
//                                    it.putExtra("Payment Type", result.getResponse().getPaymentType());
//                                    it.putExtra("Status Code", result.getResponse().getStatusCode());
//                                    it.putExtra("Transaction ID", result.getResponse().getTransactionId());
//                                    it.putExtra("Bank", result.getResponse().getAccountNumbers().get(0).getBank());
//                                    it.putExtra("Expire", result.getResponse().getPermataExpiration());
//                                    startActivity(it);

                                    break;
                                case TransactionResult.STATUS_FAILED:
                                    Toast.makeText(act_checkout.this, "Transaction Failed. ID: " + result.getResponse().getTransactionId() + ". Message: " + result.getResponse().getStatusMessage(), Toast.LENGTH_LONG).show();
                                    break;
                            }
                            result.getResponse().getValidationMessages();
                        } else if (result.isTransactionCanceled()) {
                            Toast.makeText(act_checkout.this, "Transaction Canceled", Toast.LENGTH_LONG).show();
                        } else {
                            if (result.getStatus().equalsIgnoreCase(TransactionResult.STATUS_INVALID)) {
                                Toast.makeText(act_checkout.this, "Transaction Invalid", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(act_checkout.this, "Transaction Finished with failure.", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }) // set transaction finish callback (sdk callback)
                .setMerchantBaseUrl(base_url) //set merchant url (required)
                .enableLog(true) // enable sdk log (optional)
                .setColorTheme(new CustomColorTheme("#FF983C", "#FF983C", "#EF4B4B")) // set theme. it will replace theme on snap theme on MAP ( optional)
                .buildSDK();
    }

    public void pilihPengiriman() {
        final Dialog dialog = new Dialog(act_checkout.this);
        dialog.setTitle("Gambar Barang");
        View v = getLayoutInflater().inflate(R.layout.popup_pilih_pengiriman, null);
        dialog.setContentView(v);
        ImageView close = v.findViewById(R.id.close);
        kurir.clear();
        kurir.add(R.drawable.logo_jne);
        kurir.add(R.drawable.logo_jnt);
        kurir.add(R.drawable.logo_pos);
        kurir.add(R.drawable.rt_checkout_cod);
//        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(act_checkout.this, android.R.layout.simple_expandable_list_item_1, kurir);
//        adapter.setDropDownViewResource(R.layout.spinner_kurir);
        AdapterKurir adapter = new AdapterKurir(act_checkout.this, kurir);
        list_pengiriman = v.findViewById(R.id.list_pengiriman);
        list_pengiriman.setAdapter(adapter);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        list_pengiriman.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    a = "jne";
                    nama_kurir.setImageResource(R.drawable.logo_jne);
                    getOngkir("160", session.getKdKecamatan(), "1000", a);
                } else if (position == 1) {
                    nama_kurir.setImageResource(R.drawable.logo_jnt);
                    a = "jnt";
                    getOngkir("160", session.getKdKecamatan(), "1000", a);
                } else if (position == 2) {
                    nama_kurir.setImageResource(R.drawable.logo_pos);
                    a = "pos";
                    getOngkir("160", session.getKdKecamatan(), "1000", a);
                } else {
                    nama_kurir.setImageResource(R.drawable.rt_checkout_cod);
                    a = "cod";

                    initialLat = Double.parseDouble(session.getLat());
                    initialLong = Double.parseDouble(session.getLong());
                    Double hasil = CalculationByDistance(initialLat, initialLong, finalLat, finalLong);
//                    Double hasil1 = toRadians(hasil);
                    if (session.getLat().equals("0") || session.getLong().equals("0")) {
                        Toasty.error(act_checkout.this, "Maaf lokasi Anda error", Toast.LENGTH_SHORT).show();
                        nama_kurir.setImageResource(R.drawable.logo_jne);
                        getOngkir("160", session.getKdKecamatan(), "1000", a);
                    } else {
                        System.out.println(Math.round(hasil)+"km");
                        getOngkirCod(Math.round(hasil)+"");
                    }
                }

                dialog.dismiss();
            }
        });

        dialog.show();
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

                    service.add("Next Day ("+jarak+" km)");
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

    public void initInputPenjualan(String sts_bayar, String transaction_id) {
        String kode_barang = TextUtils.join(";", kd_brg);
        String nama_barang = TextUtils.join(";", nm_brg);
        String harga_barang = TextUtils.join(";", hrg_asli);
        String quantity = TextUtils.join(";", qty);

        System.out.println(kode_barang);
        System.out.println(nama_barang);
        System.out.println(harga_barang);
        System.out.println(quantity);
        inputPenjualan = api.inputPenjualan(no_ent, session.getIdUser(), nama_penerima.getText().toString(), alamat_pengiriman.getText().toString(),
                no_penerima.getText().toString(), total+"", "", "", "", "", netto+"",
                ongkir_total+"", a+"", "", kode_barang, nama_barang, harga_barang, quantity, "pcs", "RETAIL", sts_bayar, transaction_id);
        inputPenjualan.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    startActivity(new Intent(act_checkout.this, act_home_retail.class));
                    finish();
                } else {
                    Toasty.error(act_checkout.this, "Error, Input Data Gagal !!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toasty.error(act_checkout.this, t.getMessage()+"", Toast.LENGTH_SHORT).show();
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
                nama_kurir.setImageResource(0);
                service.clear();
                costs.clear();
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(act_checkout.this, R.layout.spinner_pengiriman, service);
                arrayAdapter.setDropDownViewResource(R.layout.spinner_pengiriman);
                servis.setAdapter(arrayAdapter);
                harga_ongkir.setText("");
                ongkir.setText("");
                jumlah_total.setText(formatRupiah.format(Double.parseDouble("0") + total));
                netto = Double.parseDouble("0") + total;
                ongkir_total = Double.parseDouble("0");
                sts_kurir = false;
            } else if (resultCode == 2) {
                tmp_nm_voucher = data.getStringExtra("nama_voucher");
                tmp_kd_voucher = data.getStringExtra("kd_voucher");
                nilai_voucher = Double.parseDouble(data.getStringExtra("nilai_voucher"));
                nama_voucher.setText(tmp_nm_voucher);
            }
        }
    }

    public double CalculationByDistance(double initialLat, double initialLong, double finalLat, double finalLong){
        int R = 6371; // km (Earth radius)
        double dLat = toRadians(finalLat-initialLat);
        double dLon = toRadians(finalLong-initialLong);
        initialLat = toRadians(initialLat);
        finalLat = toRadians(finalLat);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(initialLat) * Math.cos(finalLat);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }

    public double toRadians(double deg) {
        return deg * (Math.PI/180);
    }

}