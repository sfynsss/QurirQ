package com.mss.quriq.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Outlet {

    @SerializedName("kd_outlet")
    @Expose
    private String kdOutlet;
    @SerializedName("nama_outlet")
    @Expose
    private String namaOutlet;
    @SerializedName("keterangan")
    @Expose
    private String keterangan;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("gambar_outlet")
    @Expose
    private String gambarOutlet;

    public String getKdOutlet() {
        return kdOutlet;
    }

    public void setKdOutlet(String kdOutlet) {
        this.kdOutlet = kdOutlet;
    }

    public String getNamaOutlet() {
        return namaOutlet;
    }

    public void setNamaOutlet(String namaOutlet) {
        this.namaOutlet = namaOutlet;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getGambarOutlet() {
        return gambarOutlet;
    }

    public void setGambarOutlet(String gambarOutlet) {
        this.gambarOutlet = gambarOutlet;
    }

}
