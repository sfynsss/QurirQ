package com.asa.asri_larisso.Activity.retail;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.asa.asri_larisso.Api.Api;
import com.asa.asri_larisso.Api.RetrofitClient;
import com.asa.asri_larisso.R;
import com.asa.asri_larisso.Response.BaseResponse;
import com.asa.asri_larisso.Response.BaseResponse1;
import com.asa.asri_larisso.Session.Session;
import com.asa.asri_larisso.Table.PoinVoucher;
import com.asa.asri_larisso.Table.SettingVoucher;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_point_retail extends AppCompatActivity {

    ImageView back, barcode;
    TextView nama_pengguna, total_point, id_member_pengguna;
    ListView list_voucher;
    SwipeRefreshLayout swipe_refresh_layout;
    int point = 0;

    Session session;
    Api api;
    Call<BaseResponse1<PoinVoucher>> getPointVoucher;
    Call<BaseResponse<SettingVoucher>> getSettingVoucher;
    Call<BaseResponse> tambahVoucher;

    private ArrayList<String> nmr = new ArrayList<>();
    private ArrayList<String> nama_voucher = new ArrayList<>();
    private ArrayList<String> nilai_voucher = new ArrayList<>();
    private ArrayList<String> ketentuan = new ArrayList<>();
    private ArrayList<String> masa_berlaku = new ArrayList<>();

    AdapterRedeem adapterRedeem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_point_retail);

        nama_pengguna = findViewById(R.id.nama_pengguna);
        total_point = findViewById(R.id.total_point);
        barcode = findViewById(R.id.barcode);
        id_member_pengguna = findViewById(R.id.id_member_pengguna);
        list_voucher = findViewById(R.id.list_voucher);
        swipe_refresh_layout = findViewById(R.id.swipe_refresh_layout);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        session = new Session(act_point_retail.this);
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());

        nama_pengguna.setText(session.getUsername());
        id_member_pengguna.setText(session.getKdCust());
        getBarcode();
        dataPoinVoucher();
        dataSettingVoucher();

        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dataPoinVoucher();
                dataSettingVoucher();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipe_refresh_layout.setRefreshing(false);
                    }
                }, 1000);
            }
        });

    }

    public void dataPoinVoucher(){
        getPointVoucher = api.getPointVoucher(session.getIdUser());
        getPointVoucher.enqueue(new Callback<BaseResponse1<PoinVoucher>>() {
            @Override
            public void onResponse(Call<BaseResponse1<PoinVoucher>> call, Response<BaseResponse1<PoinVoucher>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getData().getPoint() == null){
                        total_point.setText("0");
                    } else {
                        point = response.body().getData().getPoint();
                        total_point.setText(point+"");
                    }
                } else {
                    Toasty.error(getApplicationContext(), "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse1<PoinVoucher>> call, Throwable t) {
                Toasty.error(getApplicationContext(), "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void dataSettingVoucher(){
        getSettingVoucher = api.getSettingVoucher();
        getSettingVoucher.enqueue(new Callback<BaseResponse<SettingVoucher>>() {
            @Override
            public void onResponse(Call<BaseResponse<SettingVoucher>> call, final Response<BaseResponse<SettingVoucher>> response) {
                if (response.isSuccessful()) {
                    nmr.clear();
                    nama_voucher.clear();
                    nilai_voucher.clear();
                    ketentuan.clear();
                    masa_berlaku.clear();

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        nmr.add(response.body().getData().get(i).getNmr()+"");
                        nama_voucher.add(response.body().getData().get(i).getNamaVoucher());
                        nilai_voucher.add(response.body().getData().get(i).getNilaiVoucher()+"");
                        ketentuan.add(response.body().getData().get(i).getKetentuan()+"");
                        masa_berlaku.add(response.body().getData().get(i).getMasaBerlaku()+"");
                    }

                    adapterRedeem = new AdapterRedeem(act_point_retail.this, nmr, nama_voucher, nilai_voucher, ketentuan, masa_berlaku, new AdapterRedeem.OnEditLocationListener() {
                        @Override
                        public void onClickAdapter(int position) {
                            if (point < Integer.parseInt(ketentuan.get(position))) {
                                Toasty.warning(act_point_retail.this, "Maaf Point Anda Kurang !!!", Toast.LENGTH_SHORT).show();
                            } else {
                                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
                                Date date = new Date(System.currentTimeMillis());
                                Calendar c = Calendar.getInstance();
                                String dt = formatter.format(date);
                                try {
                                    c.setTime(formatter.parse(dt));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                c.add(Calendar.DATE, Integer.parseInt(masa_berlaku.get(position)));  // number of days to add
                                dt = formatter.format(c.getTime());  // dt is now the new date

//                                System.out.println(dt+" | "+formatter.format(date));
                                tambahVoucher = api.tambahVoucher(session.getKdCust(), nama_voucher.get(position), nilai_voucher.get(position),
                                        formatter.format(date), dt, "", response.body().getData().get(position).getGambarVoucher(), ketentuan.get(position));
                                tambahVoucher.enqueue(new Callback<BaseResponse>() {
                                    @Override
                                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                                        if (response.isSuccessful()) {
                                            getBarcode();
                                            dataPoinVoucher();
                                            dataSettingVoucher();
                                            Toasty.success(act_point_retail.this, "Voucher Berhasil Anda klaim !!!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toasty.warning(act_point_retail.this, "Error, voucher gagal di klaim", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                                        Toasty.error(act_point_retail.this, "Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                    adapterRedeem.notifyDataSetChanged();
                    list_voucher.setAdapter(adapterRedeem);
                } else {
                    Toasty.error(getApplicationContext(), "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<SettingVoucher>> call, Throwable t) {
                Toasty.error(getApplicationContext(), "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getBarcode(){
        try {
            barcode();
        } catch (Exception e) {
            e.printStackTrace();;
        }
    }

    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

    private void barcode() throws WriterException {
        BitMatrix bitMatrix = multiFormatWriter.encode(id_member_pengguna.getText().toString(), BarcodeFormat.ITF, 500, 60, null);
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
        barcode.setImageBitmap(bitmap);
    }

}