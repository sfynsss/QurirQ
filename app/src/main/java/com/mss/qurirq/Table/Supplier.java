package com.mss.qurirq.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Supplier {

    @SerializedName("KD_SUPPL")
    @Expose
    private Integer kDSUPPL;
    @SerializedName("id_toko")
    @Expose
    private Integer idToko;
    @SerializedName("NM_SUPPL")
    @Expose
    private String nMSUPPL;
    @SerializedName("ALM_SUPPL")
    @Expose
    private String aLMSUPPL;
    @SerializedName("TELP1")
    @Expose
    private String tELP1;
    @SerializedName("TELP2")
    @Expose
    private String tELP2;
    @SerializedName("TARGET")
    @Expose
    private Integer tARGET;
    @SerializedName("C_PERSON")
    @Expose
    private String cPERSON;
    @SerializedName("E_MAIL")
    @Expose
    private String eMAIL;
    @SerializedName("HP")
    @Expose
    private String hP;

    public Integer getKDSUPPL() {
        return kDSUPPL;
    }

    public void setKDSUPPL(Integer kDSUPPL) {
        this.kDSUPPL = kDSUPPL;
    }

    public Integer getIdToko() {
        return idToko;
    }

    public void setIdToko(Integer idToko) {
        this.idToko = idToko;
    }

    public String getNMSUPPL() {
        return nMSUPPL;
    }

    public void setNMSUPPL(String nMSUPPL) {
        this.nMSUPPL = nMSUPPL;
    }

    public String getALMSUPPL() {
        return aLMSUPPL;
    }

    public void setALMSUPPL(String aLMSUPPL) {
        this.aLMSUPPL = aLMSUPPL;
    }

    public String getTELP1() {
        return tELP1;
    }

    public void setTELP1(String tELP1) {
        this.tELP1 = tELP1;
    }

    public String getTELP2() {
        return tELP2;
    }

    public void setTELP2(String tELP2) {
        this.tELP2 = tELP2;
    }

    public Integer getTARGET() {
        return tARGET;
    }

    public void setTARGET(Integer tARGET) {
        this.tARGET = tARGET;
    }

    public String getCPERSON() {
        return cPERSON;
    }

    public void setCPERSON(String cPERSON) {
        this.cPERSON = cPERSON;
    }

    public String getEMAIL() {
        return eMAIL;
    }

    public void setEMAIL(String eMAIL) {
        this.eMAIL = eMAIL;
    }

    public String getHP() {
        return hP;
    }

    public void setHP(String hP) {
        this.hP = hP;
    }


}
