package com.asa.asri_larisso.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserToko {

    @SerializedName("no")
    @Expose
    private Integer no;
    @SerializedName("id_user")
    @Expose
    private Integer idUser;
    @SerializedName("id_toko")
    @Expose
    private Integer idToko;
    @SerializedName("nama_toko")
    @Expose
    private String namaToko;

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdToko() {
        return idToko;
    }

    public void setIdToko(Integer idToko) {
        this.idToko = idToko;
    }

    public String getNamaToko() {
        return namaToko;
    }

    public void setNamaToko(String namaToko) {
        this.namaToko = namaToko;
    }

}
