package com.asa.asri_larisso.Activity.retail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.asa.asri_larisso.Api.Api;
import com.asa.asri_larisso.Api.RetrofitClient;
import com.asa.asri_larisso.R;
import com.asa.asri_larisso.Session.Session;

public class act_customer_service_retail extends AppCompatActivity {

    ImageView back;
    Button hubungi_wa, hubungi_telpon;

    Session session;
    Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_customer_service_retail);

        back = findViewById(R.id.back);
        hubungi_wa = findViewById(R.id.hubungi_wa);
        hubungi_telpon = findViewById(R.id.hubungi_telpon);

        session = new Session(act_customer_service_retail.this);
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        hubungi_wa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noTelp = "6281232349898";
                String pesan = "Hallo, admin. Saya " + session.getUsername() + ",pengguna LaRisso Apps. Ingin menanyakan informasi mengenai ";
                String url = "https://api.whatsapp.com/send?phone="+ noTelp + "&text=" + pesan;

                Intent kirimWA = new Intent(Intent.ACTION_VIEW);
                kirimWA.setPackage("com.whatsapp"); //com.whatsapp or com.whatsapp.w4b
                kirimWA.setData(Uri.parse(url));
                startActivity(kirimWA);

            }
        });

        hubungi_telpon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String no_telepon = "081232349898";
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", no_telepon, null)));
            }
        });

    }
}