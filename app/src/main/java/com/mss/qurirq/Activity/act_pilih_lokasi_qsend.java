package com.mss.qurirq.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.mss.qurirq.Api.Api;
import com.mss.qurirq.Api.RetrofitClient;
import com.mss.qurirq.R;
import com.mss.qurirq.Session.Session;
import com.mss.qurirq.Table.Jarak;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_pilih_lokasi_qsend extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MapsActivity";
    ListView lstPlaces;
    private PlacesClient mPlacesClient;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;
    private final LatLng mDefaultLocation = new LatLng(-8.16918334514732, 113.70229443588259);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    private static final int M_MAX_ENTRIES = 5;
    private String[] mLikelyPlaceNames;
    private String[] mLikelyPlaceAddresses;
    private String[] mLikelyPlaceAttributions;
    private LatLng[] mLikelyPlaceLatLngs;
    private GoogleMap mMap;

    EditText cari_lokasi;
    AppCompatEditText detail_lokasi, penerima, no_telp;
    TextView nama_alamat, detail_alamat;
    Button simpan_lokasi;
    LinearLayout pick_location;
    ImageView back;

    Api api;
    Session session;
    Call<Jarak> getJarak;

    static String before(String value, String a) {
        // Return substring containing all characters before a string.
        int posA = value.indexOf(a);
        if (posA == -1) {
            return "";
        }
        return value.substring(0, posA);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_pilih_lokasi_qsend);

        String apiKey = getString(R.string.google_maps_key);
        Places.initialize(getApplicationContext(), apiKey);
        mPlacesClient = Places.createClient(this);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        nama_alamat = findViewById(R.id.nama_alamat);
        detail_alamat = findViewById(R.id.detail_alamat);
        detail_lokasi = findViewById(R.id.detail_lokasi);
        penerima = findViewById(R.id.penerima);
        no_telp = findViewById(R.id.no_telp);
        simpan_lokasi = findViewById(R.id.simpan_lokasi);
        pick_location = findViewById(R.id.pick_location);
        back = findViewById(R.id.back);
        cari_lokasi = findViewById(R.id.cari_lokasi);
        cari_lokasi.setFocusable(false);
//        double radius = 50;
        cari_lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS,
                        Place.Field.LAT_LNG);

                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, placeFields)
                        .build(act_pilih_lokasi_qsend.this);
                startActivityForResult(intent, 100);
            }
        });

        session = new Session(act_pilih_lokasi_qsend.this);
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());
        getJarak = api.getJarak("-8.2045512,113.5314046", "-8.1580916,113.6761221", "AIzaSyBhYpivDh3X593xIjPmfgqiMP3eB6KSbZM");
        getJarak.enqueue(new Callback<Jarak>() {
            @Override
            public void onResponse(Call<Jarak> call, Response<Jarak> response) {
                if (response.isSuccessful()) {
                    System.out.println(before(response.body().getRows().get(0).getElements().get(0).getDistance().getText(), " "));
                } else {
                    Toast.makeText(act_pilih_lokasi_qsend.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Jarak> call, Throwable t) {
                Toast.makeText(act_pilih_lokasi_qsend.this, t.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });

        simpan_lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(detail_lokasi.getText())) {
                    detail_lokasi.setError("Tambahkan detail lokasi");
                } else if (TextUtils.isEmpty(penerima.getText())) {
                    penerima.setError("Tambahkan nama penerima");
                } else if (TextUtils.isEmpty(no_telp.getText())) {
                    no_telp.setError("Tambahkan nomer telepon penerima");
                } else {
                    System.out.println(mDefaultLocation.latitude+" | "+mDefaultLocation.longitude);
                    Intent it = new Intent();
                    it.putExtra("latitude", mDefaultLocation.latitude+"");
                    it.putExtra("longitude", mDefaultLocation.longitude+"");
                    it.putExtra("nama_alamat", nama_alamat.getText().toString());
                    it.putExtra("detail_alamat", detail_alamat.getText().toString());
                    it.putExtra("detail_lokasi", detail_lokasi.getText().toString());
                    it.putExtra("penerima", penerima.getText().toString());
                    it.putExtra("no_telp", no_telp.getText().toString());
                    setResult(RESULT_OK, it);
                    finish();
                }
            }
        });

        pick_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickCurrentPlace();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.clear();
        LatLng sydney = new LatLng(-8.16918334514732, 113.70229443588259);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                mMap.clear();
                LatLng position = new LatLng(latLng.latitude, latLng.longitude);
                mMap.addMarker(new MarkerOptions().position(position));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
            }
        });

        mMap.getUiSettings().setZoomControlsEnabled(true);

        getLocationPermission();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            nama_alamat.setText(place.getName());
            detail_alamat.setText(place.getAddress());
            mMap.clear();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(place.getLatLng().latitude, place.getLatLng().longitude), DEFAULT_ZOOM));
            mMap.addMarker(new MarkerOptions()
                    .title("Lokasi Saat Ini")
                    .position(new LatLng(place.getLatLng().latitude, place.getLatLng().longitude))
                    .snippet("No places found, because location permission is disabled."));
        }
    }

    private void getCurrentPlaceLikelihoods() {
        List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS,
                Place.Field.LAT_LNG);

        @SuppressWarnings("MissingPermission") final FindCurrentPlaceRequest request =
                FindCurrentPlaceRequest.builder(placeFields).build();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<FindCurrentPlaceResponse> placeResponse = mPlacesClient.findCurrentPlace(request);
        placeResponse.addOnCompleteListener(this,
                new OnCompleteListener<FindCurrentPlaceResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<FindCurrentPlaceResponse> task) {
                        if (task.isSuccessful()) {
                            FindCurrentPlaceResponse response = task.getResult();
                            // Set the count, handling cases where less than 5 entries are returned.
                            int count;
                            if (response.getPlaceLikelihoods().size() < M_MAX_ENTRIES) {
                                count = response.getPlaceLikelihoods().size();
                            } else {
                                count = M_MAX_ENTRIES;
                            }

                            int i = 0;
                            mLikelyPlaceNames = new String[count];
                            mLikelyPlaceAddresses = new String[count];
                            mLikelyPlaceAttributions = new String[count];
                            mLikelyPlaceLatLngs = new LatLng[count];

                            for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
                                Place currPlace = placeLikelihood.getPlace();
                                mLikelyPlaceNames[i] = currPlace.getName();
                                mLikelyPlaceAddresses[i] = currPlace.getAddress();
                                mLikelyPlaceAttributions[i] = (currPlace.getAttributions() == null) ?
                                        null : TextUtils.join(" ", currPlace.getAttributions());
                                mLikelyPlaceLatLngs[i] = currPlace.getLatLng();

                                String currLatLng = (mLikelyPlaceLatLngs[i] == null) ?
                                        "" : mLikelyPlaceLatLngs[i].toString();

                                Log.i(TAG, String.format("Place " + currPlace.getName()
                                        + " has likelihood: " + placeLikelihood.getLikelihood()
                                        + " at " + currLatLng));

                                i++;
                                if (i > (count - 1)) {
                                    break;
                                }
                            }
                        } else {
                            Exception exception = task.getException();
                            if (exception instanceof ApiException) {
                                ApiException apiException = (ApiException) exception;
                                Log.e(TAG, "Place not found: " + apiException.getStatusCode());
                            }
                        }
                    }
                });
    }

    private void pickCurrentPlace() {
        if (mMap == null) {
            return;
        }

        if (mLocationPermissionGranted) {
            getDeviceLocation();
        } else {
            Log.i(TAG, "The user did not grant location permission.");
            System.out.println(mDefaultLocation+"");
            mMap.clear();
            mMap.addMarker(new MarkerOptions()
                    .title("Lokasi Saat Ini")
                    .position(mDefaultLocation)
                    .snippet("No places found, because location permission is disabled."));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mDefaultLocation));
            mMap.getUiSettings().setZoomControlsEnabled(true);
            getLocationPermission();
        }
    }

    private void getDeviceLocation() {
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();
                            Log.d(TAG, "Latitude: " + mLastKnownLocation.getLatitude());
                            Log.d(TAG, "Longitude: " + mLastKnownLocation.getLongitude());
                            mMap.clear();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            mMap.addMarker(new MarkerOptions()
                                    .title("Lokasi Saat Ini")
                                    .position(new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()))
                                    .snippet("No places found, because location permission is disabled."));
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                        }

                        getCurrentPlaceLikelihoods();
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void getLocationPermission() {
        mLocationPermissionGranted = false;
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }
}