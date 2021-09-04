package com.mss.quriq.Activity.retail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mss.quriq.Api.Api;
import com.mss.quriq.Api.RetrofitClient;
import com.mss.quriq.R;
import com.mss.quriq.Response.BaseResponse;
import com.mss.quriq.Response.UserResponse;
import com.mss.quriq.Session.Session;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_login extends AppCompatActivity {

    Button btn_masuk;
    TextView btn_daftar, lupa_password;
    EditText username, password;
    ProgressBar progressBar;
    Api api, api2;
    Call<BaseResponse> updateToken;
    Call<UserResponse> call;
    Session session;
    ImageView show_password;
    Boolean showPasswordClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_login);

        btn_masuk = findViewById(R.id.btn_masuk);
        btn_daftar = findViewById(R.id.daftar);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        lupa_password = findViewById(R.id.lupa_password);
        progressBar = findViewById(R.id.progress_login);

        session = new Session(this);
        api = RetrofitClient.createService(Api.class);

        btn_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("onClick");
                if (TextUtils.isEmpty(username.getText()) && TextUtils.isEmpty(password.getText())) {
                    username.setError("Email wajib diisi");
                    password.setError("Password wajib diisi");
                } else if (TextUtils.isEmpty(username.getText())) {
                    username.setError("Email wajib diisi");
                } else if (TextUtils.isEmpty(password.getText())) {
                    password.setError("Password wajib diisi");
                } else {
                    final String v_user = username.getText().toString().trim();
                    final String v_pass = password.getText().toString().trim();

                    progressBar.setVisibility(View.VISIBLE);
                    call = api.login(v_user, v_pass);
                    call.enqueue(new Callback<UserResponse>() {
                        @Override
                        public void onResponse(Call<UserResponse> call, final Response<UserResponse> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getUser().getEmailActivation().equals("1")) {
//                                    Toasty.success(getApplicationContext(), "Selamat Datang " + response.body().getUser().getName(), Toast.LENGTH_SHORT).show();
                                    session.setUserStatus(true, response.body().getUser().getId() + "",
                                            response.body().getUser().getName() + "",
                                            response.body().getUser().getEmail() + "",
                                            response.body().getUser().getApiToken() + "",
                                            response.body().getUser().getOtoritas() + "",
                                            response.body().getUser().getJNSKELAMIN()+"");
                                    session.setKdCust(response.body().getUser().getKDCUST());
                                    String latitude = "";
                                    String longitude = "";
                                    if(response.body().getUser().getLatitude() == null){
                                        latitude = "0";
                                        longitude = "0";
                                    } else {
                                        latitude = response.body().getUser().getLatitude().toString();
                                        longitude = response.body().getUser().getLongitude().toString();
                                    }
                                    session.setUserActivation(true);
                                    session.setAlamat(
                                            response.body().getUser().getNamaPenerima(),
                                            response.body().getUser().getProvinsi(),
                                            response.body().getUser().getKota(),
                                            response.body().getUser().getKecamatan(),
                                            response.body().getUser().getKdProvinsi(),
                                            response.body().getUser().getKdKota(),
                                            response.body().getUser().getKdKecamatan(),
                                            response.body().getUser().getAlamatLengkap(),
                                            response.body().getUser().getKodePos(),
                                            latitude,
                                            longitude);
                                    session.setNoTelp(response.body().getUser().getNoTelp());
                                    System.out.println(response.body().getUser().getId() + "");
                                    api2 = RetrofitClient.createServiceWithAuth(Api.class, response.body().getUser().getApiToken() + "");
                                    FirebaseInstanceId.getInstance().getInstanceId()
                                            .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                                    if (task.isSuccessful()) {
                                                        String firebase_token = task.getResult().getToken();
                                                        updateToken = api2.updateToken(response.body().getUser().getId() + "", firebase_token);
                                                        updateToken.enqueue(new Callback<BaseResponse>() {
                                                            @Override
                                                            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                                                                if (response.isSuccessful()) {
                                                                    startActivity(new Intent(act_login.this, act_home.class));
//                                                                    startActivity(new Intent(act_login.this, act_pilih_outlet_retail.class));
                                                                    finish();
                                                                } else {
                                                                    startActivity(new Intent(act_login.this, act_home.class));
                                                                    finish();
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<BaseResponse> call, Throwable t) {

                                                            }
                                                        });
                                                    } else {
                                                        Toasty.error(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                } else {
                                    session.setUserStatus(true, response.body().getUser().getId()+"",
                                            response.body().getUser().getName()+"",
                                            response.body().getUser().getEmail()+"",
                                            response.body().getUser().getApiToken()+"",
                                            response.body().getUser().getOtoritas()+"",
                                            response.body().getUser().getJNSKELAMIN()+"");
                                    session.setUserActivation(false);
                                    Intent it = new Intent(act_login.this, act_home.class);
                                    it.putExtra("email", response.body().getUser().getEmail()+"");
                                    startActivity(it);
                                    finish();
                                    Toasty.error(getApplicationContext(), "Email belum di aktifasi, silahkan cek e-mail untuk aktifasi.", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            } else {
//                                Toasty.error(getApplicationContext(), "Error Bro"+response.code(), Toast.LENGTH_SHORT).show();
                                if (response.code() == 401) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toasty.error(getApplicationContext(), "Login Gagal, Username / Password Salah !!", Toast.LENGTH_SHORT).show();
                                } else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toasty.error(getApplicationContext(), "Login Gagal, Username / Password Salah !!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<UserResponse> call, Throwable t) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toasty.error(act_login.this, "Ini error juga bos" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        lupa_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(act_login.this, act_lupa_password.class));
            }
        });

        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(act_login.this, act_register_retail.class));
            }
        });

        show_password = findViewById(R.id.show_password);
        // Default button, if need set it in xml via background="@drawable/default"
        show_password.setBackgroundResource(R.drawable.ic_eye_open_24);
        show_password.setOnClickListener(mToggleShowPasswordButton);
    }

    View.OnClickListener mToggleShowPasswordButton = new View.OnClickListener(){

        @Override
        public void onClick(View v){
            // change your button background

            if(showPasswordClicked){
                v.setBackgroundResource(R.drawable.ic_eye_closed_24);
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }else{
                v.setBackgroundResource(R.drawable.ic_eye_open_24);
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }

            showPasswordClicked = !showPasswordClicked; // reverse
        }

    };
}
