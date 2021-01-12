package com.asa.asri_larisso.Activity.retail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.asa.asri_larisso.R;

public class act_pilih_outlet_retail extends AppCompatActivity {

    LinearLayout outlet_larisso, outlet_labaku, outlet_bellio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_pilih_outlet_retail);

        outlet_larisso = findViewById(R.id.outlet_larisso);
        outlet_labaku = findViewById(R.id.outlet_labaku);
        outlet_bellio = findViewById(R.id.outlet_bellio);

        outlet_larisso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        outlet_labaku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        outlet_bellio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}