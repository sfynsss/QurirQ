package com.mss.quriq.Activity.retail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.mss.quriq.Api.Api;
import com.mss.quriq.Api.RetrofitClient;
import com.mss.quriq.R;
import com.mss.quriq.Response.BaseResponse;
import com.mss.quriq.Response.BaseResponse1;
import com.mss.quriq.Session.Session;
import com.mss.quriq.Table.MstJual;
import com.mss.quriq.Table.StsTransaksi;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_history_transaksi extends AppCompatActivity {

    ListView list_transaksi;
    Button filter_semua_diskon, filter_transaksi_berhasil, filter_menunggu_pembayaran, filter_transaksi_dibatalkan;
    Session session;
    Api api;
    Call<BaseResponse<MstJual>> getDataTransaksi;
    Call<BaseResponse<MstJual>> getDataTransaksiSukses;
    Call<BaseResponse<MstJual>> getDataTransaksiPending;
    Call<BaseResponse<MstJual>> getDataTransaksiBatal;
    Call<BaseResponse1<StsTransaksi>> getStatusTransaksi;

    ArrayList<String> no_ent = new ArrayList<>();
    ArrayList<String> jml_item = new ArrayList<>();
    ArrayList<String> tgl_transaksi = new ArrayList<>();
    ArrayList<String> waktu_transaksi = new ArrayList<>();
    ArrayList<String> jns_pengiriman = new ArrayList<>();
    ArrayList<String> disc_value = new ArrayList<>();
    ArrayList<String> ongkir = new ArrayList<>();
    ArrayList<String> subtot = new ArrayList<>();
    ArrayList<String> sts_byr = new ArrayList<>();
    ArrayList<String> payment_type = new ArrayList<>();
    ArrayList<String> bank_name = new ArrayList<>();
    ArrayList<String> va = new ArrayList<>();
    ArrayList<String> sts_transaksi = new ArrayList<>();

    AdapterTransaksi adapterTransaksi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_history_transaksi);

        list_transaksi = findViewById(R.id.list_transaksi);
        filter_semua_diskon = findViewById(R.id.filter_semua_diskon);
        filter_transaksi_berhasil = findViewById(R.id.filter_transaksi_berhasil);
        filter_menunggu_pembayaran = findViewById(R.id.filter_menunggu_pembayaran);
        filter_transaksi_dibatalkan = findViewById(R.id.filter_transaksi_dibatalkan);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        session = new Session(act_history_transaksi.this);
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());
        getDataTransaksi = api.getDataTransaksi(session.getIdUser());
        tampilTransaksi();

        filter_semua_diskon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataTransaksi = api.getDataTransaksi(session.getIdUser());
                tampilTransaksi();
                filter_semua_diskon.setBackgroundResource(R.drawable.rt_filter_bt_on_2);
                filter_semua_diskon.setTextColor(Color.parseColor("#FFFFFF"));
                filter_transaksi_berhasil.setBackgroundResource(R.drawable.rt_filter_bt_off_2);
                filter_transaksi_berhasil.setTextColor(Color.parseColor("#FFA722"));
                filter_menunggu_pembayaran.setBackgroundResource(R.drawable.rt_filter_bt_off_2);
                filter_menunggu_pembayaran.setTextColor(Color.parseColor("#FFA722"));
                filter_transaksi_dibatalkan.setBackgroundResource(R.drawable.rt_filter_bt_off_2);
                filter_transaksi_dibatalkan.setTextColor(Color.parseColor("#FFA722"));
                Toasty.success(act_history_transaksi.this, "Semua Transaksi", Toast.LENGTH_SHORT).show();
            }
        });

        filter_transaksi_berhasil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataTransaksiSukses = api.getDataTransaksiSukses(session.getIdUser());
                tampilTransaksiSukses();
                filter_transaksi_berhasil.setBackgroundResource(R.drawable.rt_filter_bt_on_2);
                filter_transaksi_berhasil.setTextColor(Color.parseColor("#FFFFFF"));
                filter_semua_diskon.setBackgroundResource(R.drawable.rt_filter_bt_off_2);
                filter_semua_diskon.setTextColor(Color.parseColor("#FFA722"));
                filter_menunggu_pembayaran.setBackgroundResource(R.drawable.rt_filter_bt_off_2);
                filter_menunggu_pembayaran.setTextColor(Color.parseColor("#FFA722"));
                filter_transaksi_dibatalkan.setBackgroundResource(R.drawable.rt_filter_bt_off_2);
                filter_transaksi_dibatalkan.setTextColor(Color.parseColor("#FFA722"));
                Toasty.success(act_history_transaksi.this, "Sortir Transaksi Berhasil", Toast.LENGTH_SHORT).show();
            }
        });

        filter_menunggu_pembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataTransaksiPending = api.getDataTransaksiPending(session.getIdUser());
                tampilTransaksiPending();
                filter_menunggu_pembayaran.setBackgroundResource(R.drawable.rt_filter_bt_on_2);
                filter_menunggu_pembayaran.setTextColor(Color.parseColor("#FFFFFF"));
                filter_semua_diskon.setBackgroundResource(R.drawable.rt_filter_bt_off_2);
                filter_semua_diskon.setTextColor(Color.parseColor("#FFA722"));
                filter_transaksi_berhasil.setBackgroundResource(R.drawable.rt_filter_bt_off_2);
                filter_transaksi_berhasil.setTextColor(Color.parseColor("#FFA722"));
                filter_transaksi_dibatalkan.setBackgroundResource(R.drawable.rt_filter_bt_off_2);
                filter_transaksi_dibatalkan.setTextColor(Color.parseColor("#FFA722"));
                Toasty.success(act_history_transaksi.this, "Sortir Menunggu Pembayaran", Toast.LENGTH_SHORT).show();
            }
        });

        filter_transaksi_dibatalkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataTransaksiBatal = api.getDataTransaksiBatal(session.getIdUser());
                tampilTransaksiBatal();
                filter_transaksi_dibatalkan.setBackgroundResource(R.drawable.rt_filter_bt_on_2);
                filter_transaksi_dibatalkan.setTextColor(Color.parseColor("#FFFFFF"));
                filter_semua_diskon.setBackgroundResource(R.drawable.rt_filter_bt_off_2);
                filter_semua_diskon.setTextColor(Color.parseColor("#FFA722"));
                filter_transaksi_berhasil.setBackgroundResource(R.drawable.rt_filter_bt_off_2);
                filter_transaksi_berhasil.setTextColor(Color.parseColor("#FFA722"));
                filter_menunggu_pembayaran.setBackgroundResource(R.drawable.rt_filter_bt_off_2);
                filter_menunggu_pembayaran.setTextColor(Color.parseColor("#FFA722"));
                Toasty.success(act_history_transaksi.this, "Sortir Transaksi Dibatalkan", Toast.LENGTH_SHORT).show();
            }
        });


        list_transaksi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(act_history_transaksi.this, act_detail_transaksi.class);
                it.putExtra("no_ent", no_ent.get(position));
                it.putExtra("tgl_transaksi", tgl_transaksi.get(position));
                it.putExtra("waktu_transaksi", waktu_transaksi.get(position));
                it.putExtra("total", subtot.get(position) + "");
                it.putExtra("sts_byr", sts_byr.get(position) + "");
                it.putExtra("jns_pengiriman", jns_pengiriman.get(position));
                it.putExtra("ongkir", ongkir.get(position) + "");
                it.putExtra("nilai_voucher", disc_value.get(position) + "");
                it.putExtra("sts_transaksi", sts_transaksi.get(position));
                startActivity(it);
            }
        });
    }

    public void tampilTransaksi(){
        getDataTransaksi.enqueue(new Callback<BaseResponse<MstJual>>() {
            @Override
            public void onResponse(Call<BaseResponse<MstJual>> call, Response<BaseResponse<MstJual>> response) {
                if (response.isSuccessful()) {
                    no_ent.clear();
                    jml_item.clear();
                    tgl_transaksi.clear();
                    waktu_transaksi.clear();
                    subtot.clear();
                    ongkir.clear();
                    disc_value.clear();
                    sts_byr.clear();
                    jns_pengiriman.clear();
                    payment_type.clear();
                    bank_name.clear();
                    va.clear();
                    sts_transaksi.clear();

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        no_ent.add(response.body().getData().get(i).getNoEnt());
                        jml_item.add(response.body().getData().get(i).getJumlah());
                        tgl_transaksi.add(TextUtils.substring(response.body().getData().get(i).getTanggal(), 0, 10));
                        waktu_transaksi.add(TextUtils.substring(response.body().getData().get(i).getTanggal(), 11, 16));
                        if (!TextUtils.isEmpty(response.body().getData().get(i).getDiscValue())) {
                            disc_value.add(response.body().getData().get(i).getDiscValue());
                        } else {
                            disc_value.add("");
                        }
                        ongkir.add(response.body().getData().get(i).getOngkir());
                        sts_byr.add(response.body().getData().get(i).getStsByr());
                        subtot.add(response.body().getData().get(i).getTotal());
                        jns_pengiriman.add(response.body().getData().get(i).getJnsPengiriman());
                        payment_type.add(response.body().getData().get(i).getPaymentType());
                        bank_name.add(response.body().getData().get(i).getBankName());
                        va.add(response.body().getData().get(i).getVaNumber());
                        sts_transaksi.add(response.body().getData().get(i).getStsTransaksi());
                    }
                    System.out.println("sts_transaksi" + sts_transaksi);

                    System.out.println(waktu_transaksi);
                    adapterTransaksi = new AdapterTransaksi(act_history_transaksi.this, no_ent, jml_item, tgl_transaksi, subtot, sts_byr, jns_pengiriman, sts_transaksi, new AdapterTransaksi.OnEditLocationListener() {
                        @Override
                        public void onClickAdapter(int position) {
                            if (sts_transaksi.get(position).equals("SELESAI")) {
                                final SweetAlertDialog pDialog = new SweetAlertDialog(act_history_transaksi.this, SweetAlertDialog.PROGRESS_TYPE);
                                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                pDialog.setTitleText("Loading");
                                pDialog.show();
                                final SweetAlertDialog dialog = new SweetAlertDialog(act_history_transaksi.this, SweetAlertDialog.SUCCESS_TYPE);
                                dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                dialog.setTitleText("Pesanan Selesai");
                                dialog.setContentText("Terimakasih telah berbelanja di LARISSO !!!");
                                dialog.setConfirmText("OKE");
                                dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        dialog.dismiss();
                                        pDialog.dismiss();
                                    }
                                });
                                dialog.show();
                            } else if (sts_transaksi.get(position).equals("BATAL")) {
                                final SweetAlertDialog pDialog = new SweetAlertDialog(act_history_transaksi.this, SweetAlertDialog.PROGRESS_TYPE);
                                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                pDialog.setTitleText("Loading");
                                pDialog.show();
                                final SweetAlertDialog dialog = new SweetAlertDialog(act_history_transaksi.this, SweetAlertDialog.WARNING_TYPE);
                                dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                dialog.setTitleText("Pesanan Batal");
                                dialog.setContentText("Pesanan Anda telah dibatalkan !!!");
                                dialog.setConfirmText("OKE");
                                dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        dialog.dismiss();
                                        pDialog.dismiss();
                                    }
                                });
                                dialog.show();
                            } else if (sts_byr.get(position).equals("0")) {
                                Intent it = new Intent(act_history_transaksi.this, act_status_pembayaran.class);
                                it.putExtra("payment_type", payment_type.get(position));
                                it.putExtra("payment_bank", bank_name.get(position));
                                it.putExtra("va", va.get(position));
                                it.putExtra("total", subtot.get(position));
                                startActivity(it);
                            } else if (sts_byr.get(position).equals("1")) {
                                Toasty.success(act_history_transaksi.this, "Transaksi Sudah Terbayar", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    adapterTransaksi.notifyDataSetChanged();
                    list_transaksi.setAdapter(adapterTransaksi);
                } else {
                    Toasty.error(act_history_transaksi.this, "Anda belum bertransaksi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<MstJual>> call, Throwable t) {
                Toasty.error(act_history_transaksi.this, t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void tampilTransaksiSukses(){
        getDataTransaksiSukses.enqueue(new Callback<BaseResponse<MstJual>>() {
            @Override
            public void onResponse(Call<BaseResponse<MstJual>> call, Response<BaseResponse<MstJual>> response) {
                if (response.isSuccessful()) {
                    no_ent.clear();
                    jml_item.clear();
                    tgl_transaksi.clear();
                    waktu_transaksi.clear();
                    subtot.clear();
                    ongkir.clear();
                    disc_value.clear();
                    sts_byr.clear();
                    jns_pengiriman.clear();
                    payment_type.clear();
                    bank_name.clear();
                    va.clear();
                    sts_transaksi.clear();

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        no_ent.add(response.body().getData().get(i).getNoEnt());
                        jml_item.add(response.body().getData().get(i).getJumlah());
                        tgl_transaksi.add(TextUtils.substring(response.body().getData().get(i).getTanggal(), 0, 10));
                        waktu_transaksi.add(TextUtils.substring(response.body().getData().get(i).getTanggal(), 11, 16));
                        if (!TextUtils.isEmpty(response.body().getData().get(i).getDiscValue())) {
                            disc_value.add(response.body().getData().get(i).getDiscValue());
                        } else {
                            disc_value.add("");
                        }
                        ongkir.add(response.body().getData().get(i).getOngkir());
                        sts_byr.add(response.body().getData().get(i).getStsByr());
                        subtot.add(response.body().getData().get(i).getTotal());
                        jns_pengiriman.add(response.body().getData().get(i).getJnsPengiriman());
                        payment_type.add(response.body().getData().get(i).getPaymentType());
                        bank_name.add(response.body().getData().get(i).getBankName());
                        va.add(response.body().getData().get(i).getVaNumber());
                        sts_transaksi.add(response.body().getData().get(i).getStsTransaksi());
                    }
                    System.out.println("sts_transaksi" + sts_transaksi);

                    System.out.println(waktu_transaksi);
                    adapterTransaksi = new AdapterTransaksi(act_history_transaksi.this, no_ent, jml_item, tgl_transaksi, subtot, sts_byr, jns_pengiriman, sts_transaksi, new AdapterTransaksi.OnEditLocationListener() {
                        @Override
                        public void onClickAdapter(int position) {
                            if (sts_transaksi.get(position).equals("SELESAI")) {
                                final SweetAlertDialog pDialog = new SweetAlertDialog(act_history_transaksi.this, SweetAlertDialog.PROGRESS_TYPE);
                                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                pDialog.setTitleText("Loading");
                                pDialog.show();
                                final SweetAlertDialog dialog = new SweetAlertDialog(act_history_transaksi.this, SweetAlertDialog.SUCCESS_TYPE);
                                dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                dialog.setTitleText("Pesanan Selesai");
                                dialog.setContentText("Terimakasih telah berbelanja di LARISSO !!!");
                                dialog.setConfirmText("OKE");
                                dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        dialog.dismiss();
                                        pDialog.dismiss();
                                    }
                                });
                                dialog.show();
                            } else if (sts_transaksi.get(position).equals("BATAL")) {
                                final SweetAlertDialog pDialog = new SweetAlertDialog(act_history_transaksi.this, SweetAlertDialog.PROGRESS_TYPE);
                                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                pDialog.setTitleText("Loading");
                                pDialog.show();
                                final SweetAlertDialog dialog = new SweetAlertDialog(act_history_transaksi.this, SweetAlertDialog.WARNING_TYPE);
                                dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                dialog.setTitleText("Pesanan Batal");
                                dialog.setContentText("Pesanan Anda telah dibatalkan !!!");
                                dialog.setConfirmText("OKE");
                                dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        dialog.dismiss();
                                        pDialog.dismiss();
                                    }
                                });
                                dialog.show();
                            } else if (sts_byr.get(position).equals("0")) {
                                Intent it = new Intent(act_history_transaksi.this, act_status_pembayaran.class);
                                it.putExtra("payment_type", payment_type.get(position));
                                it.putExtra("payment_bank", bank_name.get(position));
                                it.putExtra("va", va.get(position));
                                it.putExtra("total", subtot.get(position));
                                startActivity(it);
                            } else if (sts_byr.get(position).equals("1")) {
                                Toasty.success(act_history_transaksi.this, "Transaksi Sudah Terbayar", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    adapterTransaksi.notifyDataSetChanged();
                    list_transaksi.setAdapter(adapterTransaksi);
                } else {
                    Toasty.error(act_history_transaksi.this, "Anda belum bertransaksi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<MstJual>> call, Throwable t) {
                Toasty.error(act_history_transaksi.this, t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void tampilTransaksiPending(){
        getDataTransaksiPending.enqueue(new Callback<BaseResponse<MstJual>>() {
            @Override
            public void onResponse(Call<BaseResponse<MstJual>> call, Response<BaseResponse<MstJual>> response) {
                if (response.isSuccessful()) {
                    no_ent.clear();
                    jml_item.clear();
                    tgl_transaksi.clear();
                    waktu_transaksi.clear();
                    subtot.clear();
                    ongkir.clear();
                    disc_value.clear();
                    sts_byr.clear();
                    jns_pengiriman.clear();
                    payment_type.clear();
                    bank_name.clear();
                    va.clear();
                    sts_transaksi.clear();

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        no_ent.add(response.body().getData().get(i).getNoEnt());
                        jml_item.add(response.body().getData().get(i).getJumlah());
                        tgl_transaksi.add(TextUtils.substring(response.body().getData().get(i).getTanggal(), 0, 10));
                        waktu_transaksi.add(TextUtils.substring(response.body().getData().get(i).getTanggal(), 11, 16));
                        if (!TextUtils.isEmpty(response.body().getData().get(i).getDiscValue())) {
                            disc_value.add(response.body().getData().get(i).getDiscValue());
                        } else {
                            disc_value.add("");
                        }
                        ongkir.add(response.body().getData().get(i).getOngkir());
                        sts_byr.add(response.body().getData().get(i).getStsByr());
                        subtot.add(response.body().getData().get(i).getTotal());
                        jns_pengiriman.add(response.body().getData().get(i).getJnsPengiriman());
                        payment_type.add(response.body().getData().get(i).getPaymentType());
                        bank_name.add(response.body().getData().get(i).getBankName());
                        va.add(response.body().getData().get(i).getVaNumber());
                        sts_transaksi.add(response.body().getData().get(i).getStsTransaksi());
                    }
                    System.out.println("sts_transaksi" + sts_transaksi);

                    System.out.println(waktu_transaksi);
                    adapterTransaksi = new AdapterTransaksi(act_history_transaksi.this, no_ent, jml_item, tgl_transaksi, subtot, sts_byr, jns_pengiriman, sts_transaksi, new AdapterTransaksi.OnEditLocationListener() {
                        @Override
                        public void onClickAdapter(int position) {
                            if (sts_transaksi.get(position).equals("SELESAI")) {
                                final SweetAlertDialog pDialog = new SweetAlertDialog(act_history_transaksi.this, SweetAlertDialog.PROGRESS_TYPE);
                                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                pDialog.setTitleText("Loading");
                                pDialog.show();
                                final SweetAlertDialog dialog = new SweetAlertDialog(act_history_transaksi.this, SweetAlertDialog.SUCCESS_TYPE);
                                dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                dialog.setTitleText("Pesanan Selesai");
                                dialog.setContentText("Terimakasih telah berbelanja di LARISSO !!!");
                                dialog.setConfirmText("OKE");
                                dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        dialog.dismiss();
                                        pDialog.dismiss();
                                    }
                                });
                                dialog.show();
                            } else if (sts_transaksi.get(position).equals("BATAL")) {
                                final SweetAlertDialog pDialog = new SweetAlertDialog(act_history_transaksi.this, SweetAlertDialog.PROGRESS_TYPE);
                                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                pDialog.setTitleText("Loading");
                                pDialog.show();
                                final SweetAlertDialog dialog = new SweetAlertDialog(act_history_transaksi.this, SweetAlertDialog.WARNING_TYPE);
                                dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                dialog.setTitleText("Pesanan Batal");
                                dialog.setContentText("Pesanan Anda telah dibatalkan !!!");
                                dialog.setConfirmText("OKE");
                                dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        dialog.dismiss();
                                        pDialog.dismiss();
                                    }
                                });
                                dialog.show();
                            } else if (sts_byr.get(position).equals("0")) {
                                Intent it = new Intent(act_history_transaksi.this, act_status_pembayaran.class);
                                it.putExtra("payment_type", payment_type.get(position));
                                it.putExtra("payment_bank", bank_name.get(position));
                                it.putExtra("va", va.get(position));
                                it.putExtra("total", subtot.get(position));
                                startActivity(it);
                            } else if (sts_byr.get(position).equals("1")) {
                                Toasty.success(act_history_transaksi.this, "Transaksi Sudah Terbayar", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    adapterTransaksi.notifyDataSetChanged();
                    list_transaksi.setAdapter(adapterTransaksi);
                } else {
                    Toasty.error(act_history_transaksi.this, "Anda belum bertransaksi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<MstJual>> call, Throwable t) {
                Toasty.error(act_history_transaksi.this, t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void tampilTransaksiBatal(){
        getDataTransaksiBatal.enqueue(new Callback<BaseResponse<MstJual>>() {
            @Override
            public void onResponse(Call<BaseResponse<MstJual>> call, Response<BaseResponse<MstJual>> response) {
                if (response.isSuccessful()) {
                    no_ent.clear();
                    jml_item.clear();
                    tgl_transaksi.clear();
                    waktu_transaksi.clear();
                    subtot.clear();
                    ongkir.clear();
                    disc_value.clear();
                    sts_byr.clear();
                    jns_pengiriman.clear();
                    payment_type.clear();
                    bank_name.clear();
                    va.clear();
                    sts_transaksi.clear();

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        no_ent.add(response.body().getData().get(i).getNoEnt());
                        jml_item.add(response.body().getData().get(i).getJumlah());
                        tgl_transaksi.add(TextUtils.substring(response.body().getData().get(i).getTanggal(), 0, 10));
                        waktu_transaksi.add(TextUtils.substring(response.body().getData().get(i).getTanggal(), 11, 16));
                        if (!TextUtils.isEmpty(response.body().getData().get(i).getDiscValue())) {
                            disc_value.add(response.body().getData().get(i).getDiscValue());
                        } else {
                            disc_value.add("");
                        }
                        ongkir.add(response.body().getData().get(i).getOngkir());
                        sts_byr.add(response.body().getData().get(i).getStsByr());
                        subtot.add(response.body().getData().get(i).getTotal());
                        jns_pengiriman.add(response.body().getData().get(i).getJnsPengiriman());
                        payment_type.add(response.body().getData().get(i).getPaymentType());
                        bank_name.add(response.body().getData().get(i).getBankName());
                        va.add(response.body().getData().get(i).getVaNumber());
                        sts_transaksi.add(response.body().getData().get(i).getStsTransaksi());
                    }
                    System.out.println("sts_transaksi" + sts_transaksi);

                    System.out.println(waktu_transaksi);
                    adapterTransaksi = new AdapterTransaksi(act_history_transaksi.this, no_ent, jml_item, tgl_transaksi, subtot, sts_byr, jns_pengiriman, sts_transaksi, new AdapterTransaksi.OnEditLocationListener() {
                        @Override
                        public void onClickAdapter(int position) {
                            if (sts_transaksi.get(position).equals("SELESAI")) {
                                final SweetAlertDialog pDialog = new SweetAlertDialog(act_history_transaksi.this, SweetAlertDialog.PROGRESS_TYPE);
                                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                pDialog.setTitleText("Loading");
                                pDialog.show();
                                final SweetAlertDialog dialog = new SweetAlertDialog(act_history_transaksi.this, SweetAlertDialog.SUCCESS_TYPE);
                                dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                dialog.setTitleText("Pesanan Selesai");
                                dialog.setContentText("Terimakasih telah berbelanja di LARISSO !!!");
                                dialog.setConfirmText("OKE");
                                dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        dialog.dismiss();
                                        pDialog.dismiss();
                                    }
                                });
                                dialog.show();
                            } else if (sts_transaksi.get(position).equals("BATAL")) {
                                final SweetAlertDialog pDialog = new SweetAlertDialog(act_history_transaksi.this, SweetAlertDialog.PROGRESS_TYPE);
                                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                pDialog.setTitleText("Loading");
                                pDialog.show();
                                final SweetAlertDialog dialog = new SweetAlertDialog(act_history_transaksi.this, SweetAlertDialog.WARNING_TYPE);
                                dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                dialog.setTitleText("Pesanan Batal");
                                dialog.setContentText("Pesanan Anda telah dibatalkan !!!");
                                dialog.setConfirmText("OKE");
                                dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        dialog.dismiss();
                                        pDialog.dismiss();
                                    }
                                });
                                dialog.show();
                            } else if (sts_byr.get(position).equals("0")) {
                                Intent it = new Intent(act_history_transaksi.this, act_status_pembayaran.class);
                                it.putExtra("payment_type", payment_type.get(position));
                                it.putExtra("payment_bank", bank_name.get(position));
                                it.putExtra("va", va.get(position));
                                it.putExtra("total", subtot.get(position));
                                startActivity(it);
                            } else if (sts_byr.get(position).equals("1")) {
                                Toasty.success(act_history_transaksi.this, "Transaksi Sudah Terbayar", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    adapterTransaksi.notifyDataSetChanged();
                    list_transaksi.setAdapter(adapterTransaksi);
                } else {
                    Toasty.error(act_history_transaksi.this, "Anda belum bertransaksi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<MstJual>> call, Throwable t) {
                Toasty.error(act_history_transaksi.this, t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

}