package com.mss.qurirq.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class kategori {

    @SerializedName("kd_kat_android")
    @Expose
    private String kdKatAndroid;
    @SerializedName("nm_kat_android")
    @Expose
    private String nmKatAndroid;
    @SerializedName("sts_tampil")
    @Expose
    private Integer stsTampil;
    @SerializedName("gbr_kat_android")
    @Expose
    private String gbrKatAndroid;

    public String getKdKatAndroid() {
        return kdKatAndroid;
    }

    public void setKdKatAndroid(String kdKatAndroid) {
        this.kdKatAndroid = kdKatAndroid;
    }

    public String getNmKatAndroid() {
        return nmKatAndroid;
    }

    public void setNmKatAndroid(String nmKatAndroid) {
        this.nmKatAndroid = nmKatAndroid;
    }

    public Integer getStsTampil() {
        return stsTampil;
    }

    public void setStsTampil(Integer stsTampil) {
        this.stsTampil = stsTampil;
    }

    public String getGbrKatAndroid() {
        return gbrKatAndroid;
    }

    public void setGbrKatAndroid(String gbrKatAndroid) {
        this.gbrKatAndroid = gbrKatAndroid;
    }
}
