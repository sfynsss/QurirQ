package com.asa.asri_larisso.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MstJual {

    @SerializedName("no_ent")
    @Expose
    private String noEnt;
    @SerializedName("id_user")
    @Expose
    private Integer idUser;
    @SerializedName("sts_byr")
    @Expose
    private Integer stsByr;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;
    @SerializedName("jns_pengiriman")
    @Expose
    private String jnsPengiriman;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("jumlah")
    @Expose
    private Integer jumlah;

    public String getNoEnt() {
        return noEnt;
    }

    public void setNoEnt(String noEnt) {
        this.noEnt = noEnt;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getStsByr() {
        return stsByr;
    }

    public void setStsByr(Integer stsByr) {
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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

}
