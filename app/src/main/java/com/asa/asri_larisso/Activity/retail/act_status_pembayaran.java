package com.asa.asri_larisso.Activity.retail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.asa.asri_larisso.R;

import java.text.NumberFormat;
import java.util.Locale;

public class act_status_pembayaran extends AppCompatActivity {

    TextView payment_type, payment_bank, va, total;
    NumberFormat formatRupiah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_status_pembayaran);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(act_status_pembayaran.this, act_home_retail.class));
            }
        });

        Locale localeID = new Locale("in", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        payment_type = findViewById(R.id.payment_type);
        payment_bank = findViewById(R.id.payment_bank);
        va = findViewById(R.id.va);
        total = findViewById(R.id.total);

        payment_type.setText(getIntent().getStringExtra("payment_type"));
        payment_bank.setText(getIntent().getStringExtra("payment_bank").toUpperCase());
        va.setText(getIntent().getStringExtra("va"));
        total.setText(formatRupiah.format(Double.parseDouble(getIntent().getStringExtra("total"))).replace(",00",""));
    }
}