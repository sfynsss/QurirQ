package com.mss.qurirq.Activity.retail;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mss.qurirq.R;

import java.util.Calendar;

public class act_edit_profil_retail extends AppCompatActivity {

    EditText username , email, alamat, no_telp, password;
    TextView tgl_lahir;
    ImageView show_password, back;
    Button btn_simpan;
    Boolean showPasswordClicked = false;

    final Calendar calendar = Calendar.getInstance();
    int yy = calendar.get(Calendar.YEAR);
    int mm = calendar.get(Calendar.MONTH);
    int dd = calendar.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_edit_profil_retail);

        back = findViewById(R.id.back);
        username = findViewById(R.id.username);
        tgl_lahir = findViewById(R.id.tgl_lahir);
        email = findViewById(R.id.email);
        alamat = findViewById(R.id.alamat);
        no_telp = findViewById(R.id.no_telp);
        password = findViewById(R.id.password);
        btn_simpan = findViewById(R.id.btn_daftar);

        show_password = findViewById(R.id.show_password);
        show_password.setBackgroundResource(R.drawable.ic_eye_open_24);
        show_password.setOnClickListener(mToggleShowPasswordButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tgl_lahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(act_edit_profil_retail.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String tgl = String.valueOf(year) + "-" + String.valueOf(month + 1) + "-" + String.valueOf(dayOfMonth);
                        tgl_lahir.setText(tgl);
                    }
                }, yy, mm, dd);
                datePickerDialog.show();
            }
        });
    }

    View.OnClickListener mToggleShowPasswordButton = new View.OnClickListener(){
        @Override
        public void onClick(View v){
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