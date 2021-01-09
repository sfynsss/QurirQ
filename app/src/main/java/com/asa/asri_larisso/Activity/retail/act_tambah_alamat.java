package com.asa.asri_larisso.Activity.retail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.asa.asri_larisso.Api.Api;
import com.asa.asri_larisso.Api.RetrofitClient;
import com.asa.asri_larisso.R;
import com.asa.asri_larisso.Response.BaseResponse;
import com.asa.asri_larisso.Session.Session;
import com.asa.asri_larisso.Table.Kecamatan;
import com.asa.asri_larisso.Table.Kota;
import com.asa.asri_larisso.Table.Provinsi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_tambah_alamat extends AppCompatActivity {

    EditText nama_penerima, alamat, no_telp, kode_pos;
    Spinner provinsi, kota, kecamatan;
    Button btn_simpan;

    Api api;
    Session session;
    Call<BaseResponse> tambahAlamat;
    Call<BaseResponse<Provinsi>> getProvinsi;
    Call<BaseResponse<Kota>> getKota;
    Call<BaseResponse<Kecamatan>> getKecamatan;

    ArrayList<String> list_provinsi = new ArrayList<>();
    ArrayList<String> list_id_provinsi = new ArrayList<>();
    ArrayList<String> list_kota = new ArrayList<>();
    ArrayList<String> list_id_kota = new ArrayList<>();
    ArrayList<String> list_kecamatan = new ArrayList<>();
    ArrayList<String> list_id_kecamatan = new ArrayList<>();

    String latitude ="0";
    String longitude ="0";
    String kd_alamat = "";

    int PERMISSION_ID = 44;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_tambah_alamat);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        nama_penerima = findViewById(R.id.nama_penerima);
        no_telp = findViewById(R.id.no_telp);
        kode_pos = findViewById(R.id.kode_pos);
        alamat = findViewById(R.id.alamat);
        provinsi = findViewById(R.id.provinsi);
        kota = findViewById(R.id.kota);
        kecamatan = findViewById(R.id.kecamatan);
        btn_simpan = findViewById(R.id.btn_simpan);

        session = new Session(act_tambah_alamat.this);
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());
        getProvinsi();

        checkPermissions();
        isLocationEnabled();
        requestPermissions();

        provinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getKota(list_id_provinsi.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        kota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getKecamatan(list_id_kota.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(getIntent().getStringExtra("sts_edit"))) {
            kd_alamat = getIntent().getStringExtra("kd_alamat");
            nama_penerima.setText(getIntent().getStringExtra("nama_penerima"));
            no_telp.setText(getIntent().getStringExtra("no_telp"));
            alamat.setText(getIntent().getStringExtra("alamat"));
            kode_pos.setText(getIntent().getStringExtra("kode_pos"));
        }

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(getIntent().getStringExtra("sts_edit"))) {
                    tambahAlamat = api.ubahAlamat(
                            kd_alamat,
                            nama_penerima.getText().toString(),
                            provinsi.getSelectedItem().toString(),
                            kota.getSelectedItem().toString(),
                            kecamatan.getSelectedItem().toString(),
                            list_id_provinsi.get(provinsi.getSelectedItemPosition()),
                            list_id_kota.get(kota.getSelectedItemPosition()),
                            list_id_kecamatan.get(kecamatan.getSelectedItemPosition()),
                            alamat.getText().toString(),
                            no_telp.getText().toString(),
                            kode_pos.getText().toString(),
                            latitude, longitude);
                } else {
                    tambahAlamat = api.tambahAlamat(
                            session.getIdUser(),
                            nama_penerima.getText().toString(),
                            provinsi.getSelectedItem().toString(),
                            kota.getSelectedItem().toString(),
                            kecamatan.getSelectedItem().toString(),
                            list_id_provinsi.get(provinsi.getSelectedItemPosition()),
                            list_id_kota.get(kota.getSelectedItemPosition()),
                            list_id_kecamatan.get(kecamatan.getSelectedItemPosition()),
                            alamat.getText().toString(),
                            no_telp.getText().toString(),
                            kode_pos.getText().toString(),
                            latitude, longitude);
                }
                tambahAlamat.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful()) {
                            session.setAlamat(
                                    nama_penerima.getText().toString(),
                                    provinsi.getSelectedItem().toString(),
                                    kota.getSelectedItem().toString(),
                                    kecamatan.getSelectedItem().toString(),
                                    list_id_provinsi.get(provinsi.getSelectedItemPosition()),
                                    list_id_kota.get(kota.getSelectedItemPosition()),
                                    list_id_kecamatan.get(kecamatan.getSelectedItemPosition()),
                                    alamat.getText().toString(),
                                    kode_pos.getText().toString(),
                                    latitude, longitude);
                            session.setNoTelp(no_telp.getText().toString());
                            final SweetAlertDialog pDialog = new SweetAlertDialog(act_tambah_alamat.this, SweetAlertDialog.SUCCESS_TYPE);
                            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                            pDialog.setTitleText(response.body().getMessage());
                            pDialog.setCancelable(false);
                            pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    pDialog.dismiss();
                                    setResult(1);
                                    finish();
                                }
                            });
                            pDialog.show();
                        } else {
                            Toasty.error(act_tambah_alamat.this, "Ubah Data Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        Toasty.error(act_tambah_alamat.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void getProvinsi() {
        getProvinsi = api.getProvinsi();
        getProvinsi.enqueue(new Callback<BaseResponse<Provinsi>>() {
            @Override
            public void onResponse(Call<BaseResponse<Provinsi>> call, Response<BaseResponse<Provinsi>> response) {
                if (response.isSuccessful()) {
                    list_provinsi.clear();
                    list_id_provinsi.clear();

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        list_provinsi.add(response.body().getData().get(i).getProvince());
                        list_id_provinsi.add(response.body().getData().get(i).getProvinceId());
                    }

                    //Ini buat ngisi Spinner
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(act_tambah_alamat.this, R.layout.spinner_alamat, list_provinsi);
                    arrayAdapter.setDropDownViewResource(R.layout.spinner_alamat);
                    provinsi.setAdapter(arrayAdapter);
                    //End isi spinner
                } else {
                    Toasty.error(act_tambah_alamat.this, "Data Tidak Ditemukan !!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Provinsi>> call, Throwable t) {
                Toasty.error(act_tambah_alamat.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getKota(String id_provinsi) {
        getKota = api.getKota(id_provinsi);
        getKota.enqueue(new Callback<BaseResponse<Kota>>() {
            @Override
            public void onResponse(Call<BaseResponse<Kota>> call, Response<BaseResponse<Kota>> response) {
                if (response.isSuccessful()) {
                    list_kota.clear();
                    list_id_kota.clear();

                    for (int i = 0; i < response.body().getData().size(); i++) {
//                        list_kota.add(response.body().getData().get(i).getType()+" "+response.body().getData().get(i).getCityName());
                        list_kota.add(response.body().getData().get(i).getCityName());
                        list_id_kota.add(response.body().getData().get(i).getCityId());
                    }

                    //Ini buat ngisi Spinner
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(act_tambah_alamat.this, R.layout.spinner_alamat, list_kota);
                    arrayAdapter.setDropDownViewResource(R.layout.spinner_alamat);
                    kota.setAdapter(arrayAdapter);
                    //End isi spinner
                } else {
                    Toasty.error(act_tambah_alamat.this, "Data Tidak Ditemukan !!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Kota>> call, Throwable t) {
                Toasty.error(act_tambah_alamat.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getKecamatan(String id_kota) {
        getKecamatan = api.getKecamatan(id_kota);
        getKecamatan.enqueue(new Callback<BaseResponse<Kecamatan>>() {
            @Override
            public void onResponse(Call<BaseResponse<Kecamatan>> call, Response<BaseResponse<Kecamatan>> response) {
                if (response.isSuccessful()) {
                    list_kecamatan.clear();
                    list_id_kecamatan.clear();

                    for (int i = 0; i < response.body().getData().size(); i++) {
//                        list_kota.add(response.body().getData().get(i).getType()+" "+response.body().getData().get(i).getCityName());
                        list_kecamatan.add(response.body().getData().get(i).getSubdistrictName());
                        list_id_kecamatan.add(response.body().getData().get(i).getSubdistrictId());
                    }

                    //Ini buat ngisi Spinner
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(act_tambah_alamat.this, R.layout.spinner_alamat, list_kecamatan);
                    arrayAdapter.setDropDownViewResource(R.layout.spinner_alamat);
                    kecamatan.setAdapter(arrayAdapter);
                    //End isi spinner
                } else {
                    Toasty.error(act_tambah_alamat.this, "Data Tidak Ditemukan !!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Kecamatan>> call, Throwable t) {
                Toasty.error(act_tambah_alamat.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(this);
                mFusedLocation.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null){
                            // Do it all with location
                            Log.d("My Current location", "Lat : " + location.getLatitude() + " Long : " + location.getLongitude());
                            latitude  = location.getLatitude()+"";
                            longitude = location.getLongitude()+"";
                            System.out.println(latitude+" | "+longitude);
                            // Display in Toast
//                            Toast.makeText(KunjunganActivity.this,
//                                    "Lat : " + location.getLatitude() + " Long : " + location.getLongitude(),
//                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
            } else {
                Toasty.warning(this, "Silahkan hidupkan gps untuk menyimpan lokasi Anda.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}