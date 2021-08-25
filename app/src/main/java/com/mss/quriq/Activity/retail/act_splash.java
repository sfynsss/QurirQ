package com.mss.quriq.Activity.retail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.mss.quriq.Api.Api;
import com.mss.quriq.Api.RetrofitClient;
import com.mss.quriq.R;
import com.mss.quriq.Response.BaseResponse;
import com.mss.quriq.Session.Session;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class act_splash extends AppCompatActivity {

    Session session;
    Api api2;
    Call<BaseResponse> updateToken;
    ImageView logo;
    Animation splash_anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_splash);

        logo = findViewById(R.id.logo);
        splash_anim = AnimationUtils.loadAnimation(this, R.anim.anim_splash1);
        logo.setAnimation(splash_anim);

        session = new Session(this);
        Thread timer = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (session.getUserStatus() == true && session.getUserActivation() == true) {
                        api2 = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());
                        FirebaseInstanceId.getInstance().getInstanceId()
                                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                        if (task.isSuccessful()) {
                                            String firebase_token = task.getResult().getToken();
                                            updateToken = api2.updateToken(session.getIdUser(), firebase_token);
                                            updateToken.enqueue(new Callback<BaseResponse>() {
                                                @Override
                                                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                                                    if (response.isSuccessful()) {
                                                        System.out.println("sukses");
                                                        if (session.getStsOutlet() == false) {
                                                            startActivity(new Intent(act_splash.this, act_pilih_outlet_retail.class));
                                                        } else {
                                                            startActivity(new Intent(act_splash.this, act_home_retail.class));
                                                        }
                                                        finish();
                                                    } else {
                                                        System.out.println("gagal");
                                                        if (session.getStsOutlet() == false) {
                                                            startActivity(new Intent(act_splash.this, act_pilih_outlet_retail.class));
                                                        } else {
                                                            startActivity(new Intent(act_splash.this, act_home_retail.class));
                                                        }
                                                        finish();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<BaseResponse> call, Throwable t) {
                                                    System.out.println("sukses");
                                                    startActivity(new Intent(act_splash.this, act_home_retail.class));
                                                    finish();
                                                }
                                            });
                                        } else {
                                            System.out.println(task.getException().getMessage());
                                        }
                                    }
                                });
                    } else if(session.getUserActivation() == false) {
                        startActivity(new Intent(act_splash.this, act_login.class));
                        finish();
                    } else {
                        startActivity(new Intent(act_splash.this, act_login.class));
                        finish();
                    }
                }
            }
        };

        timer.start();
    }
}
