package com.mss.quriq.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StsTransaksi {

    @SerializedName("sts_transaksi")
    @Expose
    private String stsTransaksi;

    public String getStsTransaksi() {
        return stsTransaksi;
    }

    public void setStsTransaksi(String stsTransaksi) {
        this.stsTransaksi = stsTransaksi;
    }

}
