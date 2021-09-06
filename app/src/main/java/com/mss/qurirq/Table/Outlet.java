package com.mss.qurirq.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Outlet {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("id_kategori_outlet")
    @Expose
    private Integer idKategoriOutlet;
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
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("long")
    @Expose
    private String _long;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdKategoriOutlet() {
        return idKategoriOutlet;
    }

    public void setIdKategoriOutlet(Integer idKategoriOutlet) {
        this.idKategoriOutlet = idKategoriOutlet;
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

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLong() {
        return _long;
    }

    public void setLong(String _long) {
        this._long = _long;
    }

}
