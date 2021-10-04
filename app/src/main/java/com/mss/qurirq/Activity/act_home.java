package com.mss.qurirq.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.mss.qurirq.Api.Api;
import com.mss.qurirq.Api.RetrofitClient;
import com.mss.qurirq.R;
import com.mss.qurirq.Session.Session;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class act_home extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Session session;
    Api api;

    RelativeLayout ly_activation;
    Button btn_aktivasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_home);
        setUpNavigation();

        ly_activation = findViewById(R.id.ly_activation);
        btn_aktivasi = findViewById(R.id.btn_aktivasi);

        session = new Session(this);
        api = RetrofitClient.createService(Api.class);

        if (session.getUserActivation() == true) {
            ly_activation.setVisibility(View.GONE);
            System.out.println("Sesi Saat Ini = " + session.getUserActivation());
        } else {
            ly_activation.setVisibility(View.VISIBLE);
            System.out.println("Sesi Saat Ini = " + session.getUserActivation());
        }

        btn_aktivasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(act_home.this, act_otp_validation_retail.class);
                it.putExtra("email", session.getEmail());
                startActivity(it);
                finish();
            }
        });
    }

    public void setUpNavigation(){
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setItemIconTintList(null);
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.getNavController());
    }

}
