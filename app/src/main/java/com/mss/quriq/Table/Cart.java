package com.mss.quriq.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cart {

    @SerializedName("id_cart")
    @Expose
    private Integer idCart;
    @SerializedName("id_user")
    @Expose
    private Integer idUser;
    @SerializedName("kd_brg")
    @Expose
    private String kdBrg;
    @SerializedName("nm_brg")
    @Expose
    private String nmBrg;
    @SerializedName("satuan1")
    @Expose
    private String satuan1;
    @SerializedName("harga_jl")
    @Expose
    private Integer hargaJl;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("berat")
    @Expose
    private Double berat;
    @SerializedName("volume")
    @Expose
    private Double volume;
    @SerializedName("gambar")
    @Expose
    private String gambar;
    @SerializedName("kategori_barang")
    @Expose
    private String kategoriBarang;
    @SerializedName("sts_point")
    @Expose
    private String sts_point;

    public Integer getIdCart() {
        return idCart;
    }

    public void setIdCart(Integer idCart) {
        this.idCart = idCart;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
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

    public String getSatuan1() {
        return satuan1;
    }

    public void setSatuan1(String satuan1) {
        this.satuan1 = satuan1;
    }

    public Integer getHargaJl() {
        return hargaJl;
    }

    public void setHargaJl(Integer hargaJl) {
        this.hargaJl = hargaJl;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getBerat() {
        return berat;
    }

    public void setBerat(Double berat) {
        this.berat = berat;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getKategoriBarang() {
        return kategoriBarang;
    }

    public void setKategoriBarang(String kategoriBarang) {
        this.kategoriBarang = kategoriBarang;
    }

    public String getSts_point() {
        return sts_point;
    }

    public void setSts_point(String sts_point) {
        this.sts_point = sts_point;
    }

}
