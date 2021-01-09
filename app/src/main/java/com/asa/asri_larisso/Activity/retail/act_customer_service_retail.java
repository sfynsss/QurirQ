package com.asa.asri_larisso.Activity.retail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.asa.asri_larisso.R;

public class act_customer_service_retail extends AppCompatActivity {

    ImageView back;
    Button hubungi_wa, hubungi_telpon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_customer_service_retail);

        back = findViewById(R.id.back);
        hubungi_wa = findViewById(R.id.hubungi_wa);
        hubungi_telpon = findViewById(R.id.hubungi_telpon);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        hubungi_wa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pesan = "Hallo, admin Larisso. Saya ingin menanyakan informasi mengenai ";
                Intent kirimWA = new Intent(Intent.ACTION_SEND);
                kirimWA.setType("text/plain");
                kirimWA.putExtra(Intent.EXTRA_TEXT, pesan);
                kirimWA.putExtra("jid","6282234078999" + "@s.whatsapp.net");
                kirimWA.setPackage("com.whatsapp");

                startActivity(kirimWA);
            }
        });

        hubungi_telpon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String no_telepon = "082234078999";
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", no_telepon, null)));
            }
        });

    }
}