package com.mss.qurirq.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MstJual {

    @SerializedName("no_ent")
    @Expose
    private String noEnt;
    @SerializedName("no_resi")
    @Expose
    private String noResi;
    @SerializedName("id_user")
    @Expose
    private String idUser;
    @SerializedName("sts_byr")
    @Expose
    private String stsByr;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;
    @SerializedName("jns_pengiriman")
    @Expose
    private String jnsPengiriman;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("ongkir")
    @Expose
    private String ongkir;
    @SerializedName("disc_value")
    @Expose
    private String discValue;
    @SerializedName("jumlah")
    @Expose
    private String jumlah;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("bank_name")
    @Expose
    private String bankName;
    @SerializedName("va_number")
    @Expose
    private String vaNumber;
    @SerializedName("sts_transaksi")
    @Expose
    private String stsTransaksi;


    public String getNoEnt() {
        return noEnt;
    }

    public void setNoEnt(String noEnt) {
        this.noEnt = noEnt;
    }

    public String getNoResi() {return noResi;}

    public void setNoResi(String noResi) {this.noResi = noResi;}

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getStsByr() {
        return stsByr;
    }

    public void setStsByr(String stsByr) {
        this.stsByr = stsByr;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJnsPengiriman() {
        return jnsPengiriman;
    }

    public void setJnsPengiriman(String jnsPengiriman) {
        this.jnsPengiriman = jnsPengiriman;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getOngkir() {
        return ongkir;
    }

    public void setOngkir(String ongkir) {
        this.ongkir = ongkir;
    }

    public String getDiscValue() {
        return discValue;
    }

    public void setDiscValue(String discValue) {
        this.discValue = discValue;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getVaNumber() {
        return vaNumber;
    }

    public void setVaNumber(String vaNumber) {
        this.vaNumber = vaNumber;
    }

    public String getStsTransaksi() {
        return stsTransaksi;
    }

    public void setStsTransaksi(String stsTransaksi) {
        this.stsTransaksi = stsTransaksi;
    }

}
