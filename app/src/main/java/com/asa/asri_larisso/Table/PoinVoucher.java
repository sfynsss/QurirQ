package com.asa.asri_larisso.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PoinVoucher {
    @SerializedName("voucher")
    @Expose
    private Integer voucher;
    @SerializedName("point")
    @Expose
    private Integer point;

    public Integer getVoucher() {
        return voucher;
    }

    public void setVoucher(Integer voucher) {
        this.voucher = voucher;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

}
