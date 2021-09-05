package com.mss.qurirq.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SettingPoint {

    @SerializedName("nmr")
    @Expose
    private Integer nmr;
    @SerializedName("ketentuan")
    @Expose
    private Integer ketentuan;
    @SerializedName("nilai_point")
    @Expose
    private Integer nilaiPoint;
    @SerializedName("tgl_akhir")
    @Expose
    private String tglAkhir;
    @SerializedName("keterangan")
    @Expose
    private String keterangan;

    public Integer getNmr() {
        return nmr;
    }

    public void setNmr(Integer nmr) {
        this.nmr = nmr;
    }

    public Integer getKetentuan() {
        return ketentuan;
    }

    public void setKetentuan(Integer ketentuan) {
        this.ketentuan = ketentuan;
    }

    public Integer getNilaiPoint() {
        return nilaiPoint;
    }

    public void setNilaiPoint(Integer nilaiPoint) {
        this.nilaiPoint = nilaiPoint;
    }

    public String getTglAkhir() {
        return tglAkhir;
    }

    public void setTglAkhir(String tglAkhir) {
        this.tglAkhir = tglAkhir;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

}
