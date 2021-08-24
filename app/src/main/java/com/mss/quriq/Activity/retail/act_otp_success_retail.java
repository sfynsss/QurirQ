package com.mss.quriq.Activity.retail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mss.quriq.R;

public class act_otp_success_retail extends AppCompatActivity {

    Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_otp_success_retail);

        btn_next = findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(act_otp_success_retail.this, act_pilih_outlet_retail.class));
                finish();

//                Intent it = new Intent(act_otp_success_retail.this, act_pilih_outlet_retail.class);
//                it.putExtra("sts_aktif_otp", 1);
//                startActivity(it);
//                finish();
            }
        });
    }
}