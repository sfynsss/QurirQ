package com.mss.qurirq.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetJual {

    @SerializedName("id_det")
    @Expose
    private Integer idDet;
    @SerializedName("no_ent")
    @Expose
    private String noEnt;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;
    @SerializedName("kd_brg")
    @Expose
    private String kdBrg;
    @SerializedName("nm_brg")
    @Expose
    private String nmBrg;
    @SerializedName("harga")
    @Expose
    private Integer harga;
    @SerializedName("jumlah")
    @Expose
    private Integer jumlah;
    @SerializedName("satuan")
    @Expose
    private String satuan;
    @SerializedName("sub_total")
    @Expose
    private Integer subTotal;

    public Integer getIdDet() {
        return idDet;
    }

    public void setIdDet(Integer idDet) {
        this.idDet = idDet;
    }

    public String getNoEnt() {
        return noEnt;
    }

    public void setNoEnt(String noEnt) {
        this.noEnt = noEnt;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getKdBrg() {
        return kdBrg;
    }

    public void setKdBrg(String kdBrg) {
        this.kdBrg = kdBrg;
    }

    public String getNmBrg() {
        return nmBrg;
    }

    public void setNmBrg(String nmBrg) {
        this.nmBrg = nmBrg;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public Integer getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Integer subTotal) {
        this.subTotal = subTotal;
    }

}
