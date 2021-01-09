package com.asa.asri_larisso.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class OngkirCod {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("harga_awal")
    @Expose
    private Integer hargaAwal;
    @SerializedName("harga_per_km")
    @Expose
    private Integer hargaPerKm;
    @SerializedName("harga_per_kg")
    @Expose
    private Integer hargaPerKg;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHargaAwal() {
        return hargaAwal;
    }

    public void setHargaAwal(Integer hargaAwal) {
        this.hargaAwal = hargaAwal;
    }

    public Integer getHargaPerKm() {
        return hargaPerKm;
    }

    public void setHargaPerKm(Integer hargaPerKm) {
        this.hargaPerKm = hargaPerKm;
    }

    public Integer getHargaPerKg() {
        return hargaPerKg;
    }

    public void setHargaPerKg(Integer hargaPerKg) {
        this.hargaPerKg = hargaPerKg;
    }

}
