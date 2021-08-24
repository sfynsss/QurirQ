package com.mss.quriq.Session;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    Context context;

    public Session(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences("Brokenway", context.MODE_PRIVATE);
        this.editor = preferences.edit();
    }

    public void setLoggedIn(Boolean loggedIn) {
        editor.putBoolean("loggedIn", loggedIn);
        editor.commit();
    }

    public void setUserStatus(Boolean loggedIn, String id_user, String name, String email, String token, String otoritas, String jenis_kelamin){
        editor.putBoolean("loggedIn", loggedIn);
        editor.putString("id_user", id_user);
        editor.putString("name", name);
        editor.putString("email", email);
        editor.putString("token", token);
        editor.putString("otoritas", otoritas);
        editor.putString("JNS_KELAMIN", jenis_kelamin);
        editor.commit();
    }

    public void setUserActivation (Boolean activation) {
        editor.putBoolean("activation", activation);
        editor.commit();
    }

    public void setKdCust(String kd_cust){
        editor.putString("kd_cust", kd_cust);
        editor.commit();
    }

    public void setNoTelp(String no_telp){
        editor.putString("no_telp", no_telp);
        editor.commit();
    }

    public void setOutlet(String kd_outlet, String nama_outlet, String gambar_outlet, Boolean sts_outlet){
        editor.putString("kd_outlet", kd_outlet);
        editor.putString("nama_outlet", nama_outlet);
        editor.putString("gambar_outlet", gambar_outlet);
        editor.putBoolean("sts_outlet", sts_outlet);
        editor.commit();
    }

    public void setJenisKelamin(String jenis_kelamin){
        editor.putString("JNS_KELAMIN", jenis_kelamin);
        editor.commit();
    }

    public String getBaseUrl() {
        return preferences.getString("baseUrl", "server.larisso.co.id");
    }

    public boolean getUserStatus(){
        return preferences.getBoolean("loggedIn", false);
    }

    public boolean getUserActivation(){
        return preferences.getBoolean("activation", false);
    }

    public String getUsername(){
        return preferences.getString("name", "");
    }

    public String getEmail(){
        return preferences.getString("email", "");
    }

    public String getIdUser(){
        return preferences.getString("id_user", "");
    }

    public String getNoTelp(){
        return preferences.getString("no_telp", "");
    }

    public String getToken() {
        return preferences.getString("token", "");
    }

    public String getOtoritas() {
        return preferences.getString("otoritas", "");
    }

    public String getKdCust() {
        return preferences.getString("kd_cust", "");
    }

    public String getKdOutlet() {
        return preferences.getString("kd_outlet", "");
    }

    public String getNamaOutlet() {
        return preferences.getString("nama_outlet", "");
    }

    public String getGambarOutlet() {
        return preferences.getString("gambar_outlet", "");
    }

    public boolean getStsOutlet() {
        return preferences.getBoolean("sts_outlet", false);
    }

    public String getJenisKelamin(){ return preferences.getString("JNS_KELAMIN", ""); }

    public void setAlamat(String nama_penerima, String provinsi, String kota, String kecamatan, String kd_provinsi, String kd_kota,
                          String kd_kecamatan, String alamat, String kode_pos, String latitude, String longitude) {
        editor.putString("nama_penerima", nama_penerima);
        editor.putString("provinsi", provinsi);
        editor.putString("kota", kota);
        editor.putString("kecamatan", kecamatan);
        editor.putString("kd_provinsi", kd_provinsi);
        editor.putString("kd_kota", kd_kota);
        editor.putString("kd_kecamatan", kd_kecamatan);
        editor.putString("alamat", alamat);
        editor.putString("kode_pos", kode_pos);
        editor.putString("latitude", latitude);
        editor.putString("longitude", longitude);
        editor.commit();
    }

    public String getNamaPenerima() {
        return preferences.getString("nama_penerima", "");
    }

    public String getProvinsi() {
        return preferences.getString("provinsi", "");
    }

    public String getKota() {
        return preferences.getString("kota", "");
    }

    public String getKecamatan() {
        return preferences.getString("kecamatan", "");
    }

    public String getKdProvinsi() {
        return preferences.getString("kd_provinsi", "");
    }

    public String getKdKota() {
        return preferences.getString("kd_kota", "");
    }

    public String getKdKecamatan() {
        return preferences.getString("kd_kecamatan", "");
    }

    public String getAlamat() {
        return preferences.getString("alamat", "");
    }

    public String getKodePos() {
        return preferences.getString("kode_pos", "");
    }

    public String getLat() {
        return preferences.getString("latitude", "");
    }

    public String getLong() {
        return preferences.getString("longitude", "");
    }

}

