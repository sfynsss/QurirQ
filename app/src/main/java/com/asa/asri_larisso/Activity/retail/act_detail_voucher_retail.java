package com.asa.asri_larisso.Activity.retail;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.asa.asri_larisso.R;
import com.asa.asri_larisso.Session.Session;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class act_detail_voucher_retail extends AppCompatActivity {

    ImageView back, gambar_voucher, barcode_voucher;
    TextView judul_voucher, kode_voucher, deskripsi_voucher, tgl_berlaku, tgl_berakhir;
    Session session;
    String kd_voucher = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_detail_voucher_retail);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        gambar_voucher = findViewById(R.id.gambar_voucher);
        judul_voucher = findViewById(R.id.judul_voucher);
        deskripsi_voucher = findViewById(R.id.deskripsi_voucher);
        tgl_berlaku = findViewById(R.id.tgl_berlaku);

        session = new Session(this);
        kd_voucher = getIntent().getStringExtra("kd_voucher");
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.signature(
                new ObjectKey(String.valueOf(System.currentTimeMillis())));
        Glide.with(act_detail_voucher_retail.this)
                .setDefaultRequestOptions(requestOptions)
                .load("http://" + session.getBaseUrl() + "/storage/" + getIntent().getStringExtra("gambar") + "").into(gambar_voucher);
        judul_voucher.setText(getIntent().getStringExtra("nama_voucher"));
        tgl_berlaku.setText(getIntent().getStringExtra("tgl_berlaku"));
        deskripsi_voucher.setText(getIntent().getStringExtra("sk"));
//        getBarcode();
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
        BitMatrix bitMatrix = multiFormatWriter.encode(kode_voucher.getText().toString(), BarcodeFormat.CODE_128, 500, 60, null);
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
        barcode_voucher.setImageBitmap(bitmap);
    }

}