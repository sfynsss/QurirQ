package com.mss.quriq.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SettingVoucher {

    @SerializedName("nmr")
    @Expose
    private Integer nmr;
    @SerializedName("nama_voucher")
    @Expose
    private String namaVoucher;
    @SerializedName("gambar_voucher")
    @Expose
    private String gambarVoucher;
    @SerializedName("nilai_voucher")
    @Expose
    private Integer nilaiVoucher;
    @SerializedName("ketentuan")
    @Expose
    private Integer ketentuan;
    @SerializedName("masa_berlaku")
    @Expose
    private Integer masaBerlaku;
    @SerializedName("sk")
    @Expose
    private String sk;

    public Integer getNmr() {
        return nmr;
    }

    public void setNmr(Integer nmr) {
        this.nmr = nmr;
    }

    public String getNamaVoucher() {
        return namaVoucher;
    }

    public void setNamaVoucher(String namaVoucher) {
        this.namaVoucher = namaVoucher;
    }

    public String getGambarVoucher() {
        return gambarVoucher;
    }

    public void setGambarVoucher(String gambarVoucher) {
        this.gambarVoucher = gambarVoucher;
    }

    public Integer getNilaiVoucher() {
        return nilaiVoucher;
    }

    public void setNilaiVoucher(Integer nilaiVoucher) {
        this.nilaiVoucher = nilaiVoucher;
    }

    public Integer getKetentuan() {
        return ketentuan;
    }

    public void setKetentuan(Integer ketentuan) {
        this.ketentuan = ketentuan;
    }

    public Integer getMasaBerlaku() {
        return masaBerlaku;
    }

    public void setMasaBerlaku(Integer masaBerlaku) {
        this.masaBerlaku = masaBerlaku;
    }

    public String getSk() {
        return sk;
    }

    public void setSk(String sk) {
        this.sk = sk;
    }

}
