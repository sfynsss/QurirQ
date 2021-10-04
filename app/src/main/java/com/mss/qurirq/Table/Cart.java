package com.mss.qurirq.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cart {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("id_user")
    @Expose
    private Integer idUser;
    @SerializedName("id_barang")
    @Expose
    private Integer idBarang;
    @SerializedName("id_outlet")
    @Expose
    private Integer idOutlet;
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
    @SerializedName("gambar")
    @Expose
    private String gambar;
    @SerializedName("kategori_barang")
    @Expose
    private String kategoriBarang;
    @SerializedName("sts_jual")
    @Expose
    private String stsJual;
    @SerializedName("sts_point")
    @Expose
    private Integer stsPoint;
    @SerializedName("nama_outlet")
    @Expose
    private String namaOutlet;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(Integer idBarang) {
        this.idBarang = idBarang;
    }

    public Integer getIdOutlet() {
        return idOutlet;
    }

    public void setIdOutlet(Integer idOutlet) {
        this.idOutlet = idOutlet;
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

    public String getStsJual() {
        return stsJual;
    }

    public void setStsJual(String stsJual) {
        this.stsJual = stsJual;
    }

    public Integer getStsPoint() {
        return stsPoint;
    }

    public void setStsPoint(Integer stsPoint) {
        this.stsPoint = stsPoint;
    }

    public String getNamaOutlet() {
        return namaOutlet;
    }

    public void setNamaOutlet(String namaOutlet) {
        this.namaOutlet = namaOutlet;
    }

}
