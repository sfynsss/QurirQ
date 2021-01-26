package com.asa.asri_larisso.Activity.retail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.asa.asri_larisso.Api.Api;
import com.asa.asri_larisso.Api.RetrofitClient;
import com.asa.asri_larisso.R;
import com.asa.asri_larisso.Session.Session;

public class act_user_profile extends AppCompatActivity {

    ImageView back;
    Button btn_edit;
    TextView nama_pengguna, alamat, no_telp, email;

    Session session;
    Api service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_user_profile);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        nama_pengguna = findViewById(R.id.nama_pengguna);
        alamat = findViewById(R.id.alamat);
        no_telp = findViewById(R.id.no_telp);
        email = findViewById(R.id.email);
        btn_edit = findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(act_user_profile.this, act_coming_soon_retail.class));
            }
        });

        session = new Session(act_user_profile.this);
        service = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());

        nama_pengguna.setText(session.getUsername());
        alamat.setText(session.getAlamat());
        no_telp.setText(session.getNoTelp());
        email.setText(session.getEmail());
    }
}