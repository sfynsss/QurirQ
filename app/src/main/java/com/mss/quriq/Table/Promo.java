package com.mss.quriq.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Promo {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("tgl_mulai")
    @Expose
    private String tglMulai;
    @SerializedName("tgl_akhir")
    @Expose
    private String tglAkhir;
    @SerializedName("nama_promo")
    @Expose
    private String namaPromo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTglMulai() {
        return tglMulai;
    }

    public void setTglMulai(String  tglMulai) {
        this.tglMulai = tglMulai;
    }

    public String getTglAkhir() {
        return tglAkhir;
    }

    public void setHargaPerKm(String tglAkhir) {
        this.tglAkhir = tglAkhir;
    }

    public String getNamaPromo() {
        return namaPromo;
    }

    public void setNamaPromo(String namaPromo) {
        this.namaPromo = namaPromo;
    }

}
