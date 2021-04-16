package com.asa.asri_larisso.Activity.retail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.asa.asri_larisso.Api.Api;
import com.asa.asri_larisso.Api.RetrofitClient;
import com.asa.asri_larisso.R;
import com.asa.asri_larisso.Session.Session;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class act_home_retail extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Session session;
    Api api;

    RelativeLayout ly_activation;
    Button btn_aktivasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_home_retail);
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
                Intent it = new Intent(act_home_retail.this, act_otp_validation_retail.class);
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
