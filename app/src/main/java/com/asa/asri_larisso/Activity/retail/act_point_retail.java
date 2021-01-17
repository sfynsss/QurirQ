package com.asa.asri_larisso.Activity.retail;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asa.asri_larisso.Api.Api;
import com.asa.asri_larisso.Api.RetrofitClient;
import com.asa.asri_larisso.R;
import com.asa.asri_larisso.Response.BaseResponse1;
import com.asa.asri_larisso.Session.Session;
import com.asa.asri_larisso.Table.PoinVoucher;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_point_retail extends AppCompatActivity {

    ImageView back, barcode;
    TextView nama_pengguna, total_point, id_member_pengguna;
    int point = 0;

    Session session;
    Api service;
    Call<BaseResponse1<PoinVoucher>> getPointVoucher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_point_retail);

        nama_pengguna = findViewById(R.id.nama_pengguna);
        total_point = findViewById(R.id.total_point);
        barcode = findViewById(R.id.barcode);
        id_member_pengguna = findViewById(R.id.id_member_pengguna);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        session = new Session(act_point_retail.this);
        service = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());

        nama_pengguna.setText(session.getUsername());
        id_member_pengguna.setText(session.getIdUser());
        getBarcode();
        dataPoinVoucher();
    }

    public void dataPoinVoucher(){
        getPointVoucher = service.getPointVoucher(session.getIdUser());
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

    public void getBarcode(){
        try {
            barcode();
        } catch (Exception e) {
            e.printStackTrace();;
        }
    }

    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

    private void barcode() throws WriterException {
        BitMatrix bitMatrix = multiFormatWriter.encode(id_member_pengguna.getText().toString(), BarcodeFormat.CODE_128, 500, 60, null);
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
        barcode.setImageBitmap(bitmap);
    }

}