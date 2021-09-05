package com.mss.qurirq.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Penawaran {

    @SerializedName("id_penawaran")
    @Expose
    private Integer idPenawaran;
    @SerializedName("penawaran")
    @Expose
    private String penawaran;
    @SerializedName("gambar")
    @Expose
    private String gambar;

    public Integer getIdPenawaran() {
        return idPenawaran;
    }

    public void setIdPenawaran(Integer idPenawaran) {
        this.idPenawaran = idPenawaran;
    }

    public String getPenawaran() {
        return penawaran;
    }

    public void setPenawaran(String penawaran) {
        this.penawaran = penawaran;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }


}
