package com.asa.asri_larisso.Activity.retail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.asa.asri_larisso.Api.Api;
import com.asa.asri_larisso.Api.RetrofitClient;
import com.asa.asri_larisso.R;
import com.asa.asri_larisso.Response.BaseResponse;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_lupa_password extends AppCompatActivity {

    TextView email;
    Button btn_reset;

    Api api;
    Call<BaseResponse> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_lupa_password);

        email = findViewById(R.id.email);
        btn_reset = findViewById(R.id.btn_reset);

        api = RetrofitClient.createService(Api.class);

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call = api.forgetPassword(email.getText().toString());
                call.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful()) {
                            Toasty.success(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(act_lupa_password.this, act_login_retail.class));
                            finish();
                        } else {
                            Toasty.error(getApplicationContext(), "Reset password gagal, silahkan ulangi lagi.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        Toasty.error(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}