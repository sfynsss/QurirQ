package com.asa.asri_larisso.Activity.retail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.asa.asri_larisso.Api.Api;
import com.asa.asri_larisso.Api.RetrofitClient;
import com.asa.asri_larisso.R;
import com.asa.asri_larisso.Response.RegisterResponse;
import com.asa.asri_larisso.Session.Session;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Calendar;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_register_retail extends AppCompatActivity {

    EditText username, email, tgl_lahir, alamat, no_telp, password;
    Button btn_daftar;
    ProgressBar progressBar;

    Api api;
    Session session;
    Call <RegisterResponse> register;
    String firebase_token = "";

    final Calendar calendar = Calendar.getInstance();
    int yy = calendar.get(Calendar.YEAR);
    int mm = calendar.get(Calendar.MONTH);
    int dd = calendar.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_register_retail);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        alamat = findViewById(R.id.alamat);
        no_telp = findViewById(R.id.no_telp);
        password = findViewById(R.id.password);
        progressBar = findViewById(R.id.progress_register);
        btn_daftar = findViewById(R.id.btn_daftar);
        tgl_lahir = findViewById(R.id.tgl_lahir);

        final ProgressDialog pd = new ProgressDialog(act_register_retail.this);

        session = new Session(act_register_retail.this);
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

        tgl_lahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(act_register_retail.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String tgl = String.valueOf(year) +"-"+String.valueOf(month + 1) + "-" + String.valueOf(dayOfMonth);
                        tgl_lahir.setText(tgl);
                    }
                }, yy, mm, dd);
                datePickerDialog.show();
            }
        });

        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register = api.register(username.getText().toString()+"",  tgl_lahir.getText().toString(),"RETAIL", email.getText().toString()+"",
                        alamat.getText().toString(), no_telp.getText().toString(), password.getText().toString(), firebase_token);
//                startActivity(new Intent(act_register_retail.this, act_otp_validation_retail.class));
                progressBar.setVisibility(View.VISIBLE);
                pd.setTitle("Mohon Menunggu");
                pd.setMessage("Akun anda sedang dibuat");
                pd.setCancelable(false);
                pd.show();

                register.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if (response.isSuccessful()) {
                            progressBar.setVisibility(View.INVISIBLE);
                            session.setUserStatus(true, response.body().getRegister().getId()+"",
                                    response.body().getRegister().getName()+"",
                                    response.body().getRegister().getEmail()+"",
                                    response.body().getRegister().getApiToken()+"",
                                    response.body().getRegister().getOtoritas()+"");
                            Intent it = new Intent(act_register_retail.this, act_otp_validation_retail.class);
                            it.putExtra("email", response.body().getRegister().getEmail()+"");
                            startActivity(it);
                            pd.hide();
                            finish();
                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            pd.hide();
                            Toasty.error(getApplicationContext(), "Registrasi Gagal, email / no_telp sudah terpakai", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        pd.hide();
                        Toasty.error(act_register_retail.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("Error", "onFailure: " +t.getMessage());
                    }
                });
            }
        });

    }
}