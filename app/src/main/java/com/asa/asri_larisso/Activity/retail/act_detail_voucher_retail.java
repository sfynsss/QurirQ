package com.asa.asri_larisso.Activity.retail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.asa.asri_larisso.R;

public class act_detail_voucher_retail extends AppCompatActivity {

    ImageView back, gambar_voucher;
    TextView nama_voucher, deskripsi_voucher, tgl_mulai, tgl_berakhir;

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
        nama_voucher = findViewById(R.id.nama_voucher);
        deskripsi_voucher = findViewById(R.id.tgl_mulai);
        tgl_mulai = findViewById(R.id.tgl_mulai);
        tgl_berakhir = findViewById(R.id.tgl_berakhir);

    }
}