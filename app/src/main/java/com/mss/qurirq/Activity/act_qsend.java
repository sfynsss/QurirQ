package com.mss.qurirq.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mss.qurirq.R;

import java.util.ArrayList;

public class act_qsend extends AppCompatActivity {

    FloatingActionButton tambah_alamat, selanjutnya;
    ArrayList<String> latitude = new ArrayList<>();
    ArrayList<String> longitude = new ArrayList<>();
    ArrayList<String> nama_alamat = new ArrayList<>();
    ArrayList<String> detail_alamat = new ArrayList<>();
    ArrayList<String> detail_lokasi = new ArrayList<>();
    ArrayList<String> penerima = new ArrayList<>();
    ArrayList<String> no_telp = new ArrayList<>();
    int nomer = 0;

    RelativeLayout gambar_pengiriman;
    ListView list_alamat_pengiriman;
    AdapterQsend adapterQsend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_qsend);

        tambah_alamat = findViewById(R.id.tambah_alamat);
        tambah_alamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(act_qsend.this, act_pilih_lokasi_qsend.class), 0);
            }
        });

        selanjutnya = findViewById(R.id.selanjutnya);
        selanjutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(act_qsend.this, act_pilih_lokasi_pengirim.class));
            }
        });

        gambar_pengiriman = findViewById(R.id.gambar_pengiriman);
        list_alamat_pengiriman = findViewById(R.id.list_alamat_pengiriman);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                latitude.add(data.getStringExtra("latitude"));
                longitude.add(data.getStringExtra("longitude"));
                nama_alamat.add(data.getStringExtra("nama_alamat"));
                detail_alamat.add(data.getStringExtra("detail_alamat"));
                detail_lokasi.add(data.getStringExtra("detail_lokasi"));
                penerima.add(data.getStringExtra("penerima"));
                no_telp.add(data.getStringExtra("no_telp"));
                gambar_pengiriman.setVisibility(View.GONE);
                list_alamat_pengiriman.setVisibility(View.VISIBLE);

                adapterQsend = new AdapterQsend(act_qsend.this, penerima, detail_alamat, no_telp, new AdapterQsend.OnEditLocationListener() {
                    @Override
                    public void onClickAdapter(int position) {
                        latitude.remove(position);
                        longitude.remove(position);
                        nama_alamat.remove(position);
                        detail_alamat.remove(position);
                        detail_lokasi.remove(position);
                        penerima.remove(position);
                        no_telp.remove(position);
                        if (latitude.size() < 0) {
                            gambar_pengiriman.setVisibility(View.VISIBLE);
                            list_alamat_pengiriman.setVisibility(View.GONE);
                        }
                    }
                });
                adapterQsend.notifyDataSetChanged();
                list_alamat_pengiriman.setAdapter(adapterQsend);
            }
        }
    }
}