package com.mss.quriq.Activity.retail;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mss.quriq.R;

import java.text.NumberFormat;
import java.util.Locale;

public class act_status_pembayaran extends AppCompatActivity {

    TextView payment_type, payment_bank, va, total, salin_va;
    Button kembali_dashboard;
    NumberFormat formatRupiah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_status_pembayaran);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(act_status_pembayaran.this, act_home.class));
            }
        });

        Locale localeID = new Locale("in", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        payment_type = findViewById(R.id.payment_type);
        payment_bank = findViewById(R.id.payment_bank);
        va = findViewById(R.id.va);
        total = findViewById(R.id.total);
        kembali_dashboard = findViewById(R.id.kembali_dashboard);
        salin_va = findViewById(R.id.salin_va);

        payment_type.setText(getIntent().getStringExtra("payment_type"));
        payment_bank.setText(getIntent().getStringExtra("payment_bank").toUpperCase());
        va.setText(getIntent().getStringExtra("va"));
        total.setText(formatRupiah.format(Double.parseDouble(getIntent().getStringExtra("total"))).replace(",00",""));
        kembali_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(act_status_pembayaran.this, act_home.class));
                finish();
            }
        });
        salin_va.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("", va.getText());
                manager.setPrimaryClip(clipData);
                Toasty.success(act_status_pembayaran.this, "No. Virtual Account disalin", Toast.LENGTH_SHORT).show();
            }
        });


    }
}