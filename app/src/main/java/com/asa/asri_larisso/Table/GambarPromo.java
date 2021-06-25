package com.asa.asri_larisso.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GambarPromo {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("gambar")
    @Expose
    private String gambar;

    public Integer getIdPenawaran() {
        return id;
    }

    public void setIdPenawaran(Integer id) {
        this.id = id;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }


}
