package com.asa.asri_larisso.Activity.retail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.asa.asri_larisso.R;

public class act_detail_voucher_retail extends AppCompatActivity {

    ImageView back, gambar_voucher;
    TextView judul_voucher, deskripsi_voucher, tgl_berlaku, tgl_berakhir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_detail_voucher_retail);

//        back = findViewById(R.id.back);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//        gambar_voucher = findViewById(R.id.gambar_voucher);
//        judul_voucher = findViewById(R.id.judul_voucher);
//        deskripsi_voucher = findViewById(R.id.tgl_mulai);
//        tgl_berlaku = findViewById(R.id.tgl_berlaku);

    }
}