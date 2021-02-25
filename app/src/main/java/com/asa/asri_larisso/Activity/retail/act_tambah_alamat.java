package com.asa.asri_larisso.Activity.retail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
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
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_tambah_alamat extends AppCompatActivity {

    EditText nama_penerima, alamat, no_telp, kode_pos;
    Spinner provinsi, kota, kecamatan;
    Button btn_pin_lokasi, btn_pin_maps, btn_simpan;
    Switch lengkapi_otomatis;
    TextView koordinat;
    WifiManager wifiManager;

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

    String latitude = "0";
    String longitude = "0";
    String kd_alamat = "";
    FusedLocationProviderClient fusedLocationProviderClient;

    int PERMISSION_ID = 44;
    private final static int PLACE_PICKER_REQUEST = 999;

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
        lengkapi_otomatis = findViewById(R.id.lengkapi_otomatis);
        btn_pin_lokasi = findViewById(R.id.btn_pin_lokasi);
        btn_pin_maps = findViewById(R.id.btn_pin_maps);
        koordinat = findViewById(R.id.koordinat);

        wifiManager= (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        session = new Session(act_tambah_alamat.this);
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());
        getProvinsi();

        lengkapi_otomatis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    nama_penerima.setText(session.getUsername());
                    alamat.setText(session.getAlamat());
                    no_telp.setText(session.getNoTelp());
                }else {
                    nama_penerima.setText("");
                    alamat.setText("");
                    no_telp.setText("");
                }
            }
        });

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(act_tambah_alamat.this);

        btn_pin_lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(act_tambah_alamat.this, act_pin_location.class));
//                if (ActivityCompat.checkSelfPermission(act_tambah_alamat.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
//                        && ActivityCompat.checkSelfPermission(act_tambah_alamat.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ){
//                    getCurrentLocation();
//                } else {
//                    ActivityCompat.requestPermissions(act_tambah_alamat.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
//                }
            }
        });

        btn_pin_maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Disable Wifi
                wifiManager.setWifiEnabled(false);
                openPlacePicker();
            }
        });

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100 && grantResults.length > 0 && (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)){
            getCurrentLocation();
        } else {
            Toast.makeText(getApplicationContext(), "Permisson Dennied.", Toast.LENGTH_SHORT);
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    final Location location = task.getResult();
                    if (location != null){
                        latitude = String.valueOf(location.getLatitude());
                        longitude = String.valueOf(location.getLongitude());
                        koordinat.setText(latitude+" | "+longitude);
                    } else {
                        LocationRequest locationRequest = new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(10000)
                                .setFastestInterval(1000)
                                .setNumUpdates(1);

                        LocationCallback locationCallback = new LocationCallback(){
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                super.onLocationResult(locationResult);
                                Location location1 = locationResult.getLastLocation();
                                latitude = String.valueOf(location1.getLatitude());
                                longitude = String.valueOf(location1.getLongitude());
                                koordinat.setText(latitude+" | "+longitude);
                            }
                        };
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                    }
                }
            });
        } else {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    private void openPlacePicker() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);

            //Enable Wifi
            wifiManager.setWifiEnabled(true);

        } catch (GooglePlayServicesRepairableException e) {
            Log.d("Exception",e.getMessage());

            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.d("Exception",e.getMessage());

            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode){
                case PLACE_PICKER_REQUEST:
                    Place place = PlacePicker.getPlace(act_tambah_alamat.this, data);

                    double latitude = place.getLatLng().latitude;
                    double longitude = place.getLatLng().longitude;
                    String PlaceLatLng = String.valueOf(latitude)+" , "+String.valueOf(longitude);
                    koordinat.setText(PlaceLatLng);

            }
        }
    }


}