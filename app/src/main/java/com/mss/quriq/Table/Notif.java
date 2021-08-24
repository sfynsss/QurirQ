package com.mss.quriq.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notif {

    @SerializedName("id_det_notif")
    @Expose
    private Integer idDetNotif;
    @SerializedName("kd_cust")
    @Expose
    private String kdCust;
    @SerializedName("id_notif")
    @Expose
    private Integer idNotif;
    @SerializedName("judul")
    @Expose
    private String judul;
    @SerializedName("notif")
    @Expose
    private String notif;
    @SerializedName("jenis_notif")
    @Expose
    private Integer jenisNotif;
    @SerializedName("tgl_notif")
    @Expose
    private String tglNotif;

    public Integer getIdDetNotif() {
        return idDetNotif;
    }

    public void setIdDetNotif(Integer idDetNotif) {
        this.idDetNotif = idDetNotif;
    }

    public String getKdCust() {
        return kdCust;
    }

    public void setKdCust(String kdCust) {
        this.kdCust = kdCust;
    }

    public Integer getIdNotif() {
        return idNotif;
    }

    public void setIdNotif(Integer idNotif) {
        this.idNotif = idNotif;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getNotif() {
        return notif;
    }

    public void setNotif(String notif) {
        this.notif = notif;
    }

    public Integer getJenisNotif() {
        return jenisNotif;
    }

    public void setJenisNotif(Integer jenisNotif) {
        this.jenisNotif = jenisNotif;
    }

    public String getTglNotif() {
        return tglNotif;
    }

    public void setTglNotif(String tglNotif) {
        this.tglNotif = tglNotif;
    }

}
