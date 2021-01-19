package com.asa.asri_larisso.Activity.retail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.asa.asri_larisso.Api.Api;
import com.asa.asri_larisso.Api.RetrofitClient;
import com.asa.asri_larisso.R;
import com.asa.asri_larisso.Response.BaseResponse;
import com.asa.asri_larisso.Session.Session;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_splash);

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
                        startActivity(new Intent(act_splash.this, act_login_retail.class));
                        finish();
                    } else {
                        startActivity(new Intent(act_splash.this, act_login_retail.class));
                        finish();
                    }
                }
            }
        };

        timer.start();
    }
}
