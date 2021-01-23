package com.asa.asri_larisso.Activity.retail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.asa.asri_larisso.Api.Api;
import com.asa.asri_larisso.Api.RetrofitClient;
import com.asa.asri_larisso.R;
import com.asa.asri_larisso.Response.BaseResponse;
import com.asa.asri_larisso.Session.Session;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_otp_validation_retail extends AppCompatActivity {

    TextView email, resend;
    EditText satu, dua, tiga, empat;
    Button btn_next;
    Api api;
    Session session;
    Call<BaseResponse> aktifasi;
    Call<BaseResponse> resendAktifasi;
    String firebase_token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_otp_validation_retail);

        email = findViewById(R.id.email);
        satu = findViewById(R.id.satu);
        dua = findViewById(R.id.dua);
        tiga = findViewById(R.id.tiga);
        empat = findViewById(R.id.empat);
        btn_next = findViewById(R.id.btn_next);
        resend = findViewById(R.id.btn_next);

        email.setText(getIntent().getStringExtra("email"));
        session = new Session(act_otp_validation_retail.this);
        api = RetrofitClient.createService(Api.class);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()) {
                            firebase_token = task.getResult().getToken();
                        } else {
                            Toasty.error(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        satu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dua.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dua.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tiga.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tiga.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                empat.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        empat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                prosesRegistrasi();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prosesRegistrasi();
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendAktifasi = api.resendAktifasi(email.getText().toString() + "", firebase_token);
                resendAktifasi.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful()) {
                            session.getToken();
                            finish();
                        } else {
                            Toasty.error(getApplicationContext(), "Gagal Resend Aktifasi", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        Toasty.error(act_otp_validation_retail.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("Error", "onFailure: " + t.getMessage());
                    }
                });
            }
        });

    }

    public void prosesRegistrasi(){
        String token = satu.getText().toString()+""+dua.getText().toString()+""+tiga.getText().toString()+""+empat.getText().toString();
        aktifasi = api.aktifasi(session.getIdUser(), token);
        aktifasi.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    startActivity(new Intent(act_otp_validation_retail.this, act_otp_success_retail.class));
                    finish();
                } else {
                    Toasty.error(getApplicationContext(), "Aktifasi Gagal", Toast.LENGTH_SHORT).show();
                    session.setUserActivation(false);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toasty.error(act_otp_validation_retail.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                session.setUserActivation(false);
            }
        });
    }

}