package com.asa.asri_larisso.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Alamat {


    @SerializedName("kd_alamat")
    @Expose
    private Integer kdAlamat;
    @SerializedName("id_user")
    @Expose
    private Integer idUser;
    @SerializedName("nama_penerima")
    @Expose
    private String namaPenerima;
    @SerializedName("provinsi")
    @Expose
    private String provinsi;
    @SerializedName("kota")
    @Expose
    private String kota;
    @SerializedName("kecamatan")
    @Expose
    private String kecamatan;
    @SerializedName("kd_provinsi")
    @Expose
    private String kdProvinsi;
    @SerializedName("kd_kota")
    @Expose
    private String kdKota;
    @SerializedName("kd_kecamatan")
    @Expose
    private String kdKecamatan;
    @SerializedName("alamat_lengkap")
    @Expose
    private String alamatLengkap;
    @SerializedName("no_telp_penerima")
    @Expose
    private String noTelpPenerima;
    @SerializedName("kode_pos")
    @Expose
    private String kodePos;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;

    public Integer getKdAlamat() {
        return kdAlamat;
    }

    public void setKdAlamat(Integer kdAlamat) {
        this.kdAlamat = kdAlamat;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getNamaPenerima() {
        return namaPenerima;
    }

    public void setNamaPenerima(String namaPenerima) {
        this.namaPenerima = namaPenerima;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getKdProvinsi() {
        return kdProvinsi;
    }

    public void setKdProvinsi(String kdProvinsi) {
        this.kdProvinsi = kdProvinsi;
    }

    public String getKdKota() {
        return kdKota;
    }

    public void setKdKota(String kdKota) {
        this.kdKota = kdKota;
    }

    public String getKdKecamatan() {
        return kdKecamatan;
    }

    public void setKdKecamatan(String kdKecamatan) {
        this.kdKecamatan = kdKecamatan;
    }

    public String getAlamatLengkap() {
        return alamatLengkap;
    }

    public void setAlamatLengkap(String alamatLengkap) {
        this.alamatLengkap = alamatLengkap;
    }

    public String getNoTelpPenerima() {
        return noTelpPenerima;
    }

    public void setNoTelpPenerima(String noTelpPenerima) {
        this.noTelpPenerima = noTelpPenerima;
    }

    public String getKodePos() {
        return kodePos;
    }

    public void setKodePos(String kodePos) {
        this.kodePos = kodePos;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

}
