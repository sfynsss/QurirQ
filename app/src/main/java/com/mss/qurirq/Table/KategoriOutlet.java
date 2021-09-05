package com.mss.qurirq.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KategoriOutlet {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nm_kategori_outlet")
    @Expose
    private String nmKategoriOutlet;
    @SerializedName("gbr_kategori_outlet")
    @Expose
    private String gbrKategoriOutlet;
    @SerializedName("sts_tampil")
    @Expose
    private Integer stsTampil;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNmKategoriOutlet() {
        return nmKategoriOutlet;
    }

    public void setNmKategoriOutlet(String nmKategoriOutlet) {
        this.nmKategoriOutlet = nmKategoriOutlet;
    }

    public String getGbrKategoriOutlet() {
        return gbrKategoriOutlet;
    }

    public void setGbrKategoriOutlet(String gbrKategoriOutlet) {
        this.gbrKategoriOutlet = gbrKategoriOutlet;
    }

    public Integer getStsTampil() {
        return stsTampil;
    }

    public void setStsTampil(Integer stsTampil) {
        this.stsTampil = stsTampil;
    }

}
