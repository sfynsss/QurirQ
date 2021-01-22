package com.asa.asri_larisso.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MstJual {

    @SerializedName("no_ent")
    @Expose
    private String noEnt;
    @SerializedName("id_user")
    @Expose
    private String idUser;
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
    @SerializedName("ongkir")
    @Expose
    private Integer ongkir;
    @SerializedName("disc_value")
    @Expose
    private String discValue;
    @SerializedName("jumlah")
    @Expose
    private Integer jumlah;

    public String getNoEnt() {
        return noEnt;
    }

    public void setNoEnt(String noEnt) {
        this.noEnt = noEnt;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
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

    public Integer getOngkir() {
        return ongkir;
    }

    public void setOngkir(Integer ongkir) {
        this.ongkir = ongkir;
    }

    public String getDiscValue() {
        return discValue;
    }

    public void setDiscValue(String discValue) {
        this.discValue = discValue;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

}
