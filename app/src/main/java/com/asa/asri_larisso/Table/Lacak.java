package com.asa.asri_larisso.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lacak {

    @SerializedName("status_terkirim")
    @Expose
    private Boolean statusTerkirim;
    @SerializedName("kode_kurir")
    @Expose
    private String kodeKurir;
    @SerializedName("nama_kurir")
    @Expose
    private String namaKurir;
    @SerializedName("resi")
    @Expose
    private String resi;
    @SerializedName("tipe_pengiriman")
    @Expose
    private String tipePengiriman;
    @SerializedName("tgl_kirim")
    @Expose
    private String tglKirim;
    @SerializedName("pengirim")
    @Expose
    private String pengirim;
    @SerializedName("penerima")
    @Expose
    private String penerima;
    @SerializedName("dari")
    @Expose
    private String dari;
    @SerializedName("tujuan")
    @Expose
    private String tujuan;
    @SerializedName("status")
    @Expose
    private String status;

    public Boolean getStatusTerkirim() {
        return statusTerkirim;
    }

    public void setStatusTerkirim(Boolean statusTerkirim) {
        this.statusTerkirim = statusTerkirim;
    }

    public String getKodeKurir() {
        return kodeKurir;
    }

    public void setKodeKurir(String kodeKurir) {
        this.kodeKurir = kodeKurir;
    }

    public String getNamaKurir() {
        return namaKurir;
    }

    public void setNamaKurir(String namaKurir) {
        this.namaKurir = namaKurir;
    }

    public String getResi() {
        return resi;
    }

    public void setResi(String resi) {
        this.resi = resi;
    }

    public String getTipePengiriman() {
        return tipePengiriman;
    }

    public void setTipePengiriman(String tipePengiriman) {
        this.tipePengiriman = tipePengiriman;
    }

    public String getTglKirim() {
        return tglKirim;
    }

    public void setTglKirim(String tglKirim) {
        this.tglKirim = tglKirim;
    }

    public String getPengirim() {
        return pengirim;
    }

    public void setPengirim(String pengirim) {
        this.pengirim = pengirim;
    }

    public String getPenerima() {
        return penerima;
    }

    public void setPenerima(String penerima) {
        this.penerima = penerima;
    }

    public String getDari() {
        return dari;
    }

    public void setDari(String dari) {
        this.dari = dari;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
