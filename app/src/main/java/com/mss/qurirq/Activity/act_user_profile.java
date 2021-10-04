package com.mss.qurirq.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mss.qurirq.Api.Api;
import com.mss.qurirq.Api.RetrofitClient;
import com.mss.qurirq.R;
import com.mss.qurirq.Session.Session;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class act_user_profile extends AppCompatActivity {

    ImageView back, profil_pic;
    Button btn_edit;
    TextView nama_pengguna, alamat, no_telp, email, jenis_kelamin;

    Session session;
    Api api;

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
        profil_pic = findViewById(R.id.profil_pic);
        nama_pengguna = findViewById(R.id.nama_pengguna);
        alamat = findViewById(R.id.alamat);
        no_telp = findViewById(R.id.no_telp);
        email = findViewById(R.id.email);
        jenis_kelamin = findViewById(R.id.jenis_kelamin);
        btn_edit = findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(act_user_profile.this, act_pin_location.class));
            }
        });

        session = new Session(act_user_profile.this);
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());

        nama_pengguna.setText(session.getUsername());
        alamat.setText(session.getAlamat());
        no_telp.setText(session.getNoTelp());
        email.setText(session.getEmail());
        jenis_kelamin.setText(session.getJenisKelamin());

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        String formattedDate = df.format(c).replace("-","");

        long hariini = Integer.parseInt(formattedDate);
        long masaberlaku    = 20210312;

        if (session.getJenisKelamin().equalsIgnoreCase("laki-laki")){
            System.out.println("1");
            profil_pic.setBackgroundResource(R.drawable.rt_profil_ic_person1);
        } else {
            System.out.println("2");
            profil_pic.setBackgroundResource(R.drawable.rt_profil_ic_person2);
        }

    }
}