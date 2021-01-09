package com.asa.asri_larisso.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Voucher {

    @SerializedName("id_det_voucher")
    @Expose
    private Integer idDetVoucher;
    @SerializedName("kd_voucher")
    @Expose
    private String kdVoucher;
    @SerializedName("kd_cust")
    @Expose
    private String kdCust;
    @SerializedName("nama_voucher")
    @Expose
    private String namaVoucher;
    @SerializedName("nilai_voucher")
    @Expose
    private Integer nilaiVoucher;
    @SerializedName("tgl_berlaku_1")
    @Expose
    private String tglBerlaku1;
    @SerializedName("tgl_berlaku_2")
    @Expose
    private String tglBerlaku2;
    @SerializedName("tgl_digunakan")
    @Expose
    private String tglDigunakan;
    @SerializedName("jenis_voucher")
    @Expose
    private String jenisVoucher;
    @SerializedName("status_voucher")
    @Expose
    private String statusVoucher;
    @SerializedName("gambar")
    @Expose
    private String gambar;
    @SerializedName("sk")
    @Expose
    private String sk;

    public Integer getIdDetVoucher() {
        return idDetVoucher;
    }

    public void setIdDetVoucher(Integer idDetVoucher) {
        this.idDetVoucher = idDetVoucher;
    }

    public String getKdVoucher() {
        return kdVoucher;
    }

    public void setKdVoucher(String kdVoucher) {
        this.kdVoucher = kdVoucher;
    }

    public String getKdCust() {
        return kdCust;
    }

    public void setKdCust(String kdCust) {
        this.kdCust = kdCust;
    }

    public String getNamaVoucher() {
        return namaVoucher;
    }

    public void setNamaVoucher(String namaVoucher) {
        this.namaVoucher = namaVoucher;
    }

    public Integer getNilaiVoucher() {
        return nilaiVoucher;
    }

    public void setNilaiVoucher(Integer nilaiVoucher) {
        this.nilaiVoucher = nilaiVoucher;
    }

    public String getTglBerlaku1() {
        return tglBerlaku1;
    }

    public void setTglBerlaku1(String tglBerlaku1) {
        this.tglBerlaku1 = tglBerlaku1;
    }

    public String getTglBerlaku2() {
        return tglBerlaku2;
    }

    public void setTglBerlaku2(String tglBerlaku2) {
        this.tglBerlaku2 = tglBerlaku2;
    }

    public String getTglDigunakan() {
        return tglDigunakan;
    }

    public void setTglDigunakan(String tglDigunakan) {
        this.tglDigunakan = tglDigunakan;
    }

    public String getJenisVoucher() {
        return jenisVoucher;
    }

    public void setJenisVoucher(String jenisVoucher) {
        this.jenisVoucher = jenisVoucher;
    }

    public String getStatusVoucher() {
        return statusVoucher;
    }

    public void setStatusVoucher(String statusVoucher) {
        this.statusVoucher = statusVoucher;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getSk() {
        return sk;
    }

    public void setSk(String sk) {
        this.sk = sk;
    }
}
