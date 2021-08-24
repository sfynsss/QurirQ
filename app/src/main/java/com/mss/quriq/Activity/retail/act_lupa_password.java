package com.mss.quriq.Activity.retail;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mss.quriq.Api.Api;
import com.mss.quriq.Api.RetrofitClient;
import com.mss.quriq.R;
import com.mss.quriq.Response.BaseResponse;

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

        final ProgressDialog pd = new ProgressDialog(act_lupa_password.this);

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setTitle("Mohon Menunggu");
                pd.setMessage("Reset password sedang diproses");
                pd.setCancelable(false);
                pd.show();
                call = api.forgetPassword(email.getText().toString());
                call.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful()) {
                            Toasty.success(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(act_lupa_password.this, act_login_retail.class));
                            finish();
                            pd.hide();
                        } else {
                            pd.hide();
                            Toasty.error(getApplicationContext(), "Reset password gagal, silahkan ulangi lagi.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        pd.hide();
                        Toasty.error(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}