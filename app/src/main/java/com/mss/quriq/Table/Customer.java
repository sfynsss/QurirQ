package com.mss.quriq.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Customer {

    @SerializedName("KD_CUST")
    @Expose
    private String kDCUST;
    @SerializedName("id_toko")
    @Expose
    private Integer idToko;
    @SerializedName("NM_CUST")
    @Expose
    private String nMCUST;
    @SerializedName("ALM_CUST")
    @Expose
    private String aLMCUST;
    @SerializedName("TELP1")
    @Expose
    private String tELP1;
    @SerializedName("TELP2")
    @Expose
    private String tELP2;
    @SerializedName("KD_KAT")
    @Expose
    private String kDKAT;
    @SerializedName("JML_PIUTANG")
    @Expose
    private Integer jMLPIUTANG;
    @SerializedName("JML_BAYAR")
    @Expose
    private Integer jMLBAYAR;
    @SerializedName("SO_AWAL_PIUTANG")
    @Expose
    private Integer sOAWALPIUTANG;
    @SerializedName("E_MAIL")
    @Expose
    private String eMAIL;
    @SerializedName("C_PERSON")
    @Expose
    private String cPERSON;
    @SerializedName("TGL_LHR")
    @Expose
    private String tGLLHR;
    @SerializedName("HP")
    @Expose
    private String hP;
    @SerializedName("JNS_KELAMIN")
    @Expose
    private String jNSKELAMIN;
    @SerializedName("LEVELS")
    @Expose
    private String lEVELS;

    public String getKDCUST() {
        return kDCUST;
    }

    public void setKDCUST(String kDCUST) {
        this.kDCUST = kDCUST;
    }

    public Integer getIdToko() {
        return idToko;
    }

    public void setIdToko(Integer idToko) {
        this.idToko = idToko;
    }

    public String getNMCUST() {
        return nMCUST;
    }

    public void setNMCUST(String nMCUST) {
        this.nMCUST = nMCUST;
    }

    public String getALMCUST() {
        return aLMCUST;
    }

    public void setALMCUST(String aLMCUST) {
        this.aLMCUST = aLMCUST;
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

    public String getKDKAT() {
        return kDKAT;
    }

    public void setKDKAT(String kDKAT) {
        this.kDKAT = kDKAT;
    }

    public Integer getJMLPIUTANG() {
        return jMLPIUTANG;
    }

    public void setJMLPIUTANG(Integer jMLPIUTANG) {
        this.jMLPIUTANG = jMLPIUTANG;
    }

    public Integer getJMLBAYAR() {
        return jMLBAYAR;
    }

    public void setJMLBAYAR(Integer jMLBAYAR) {
        this.jMLBAYAR = jMLBAYAR;
    }

    public Integer getSOAWALPIUTANG() {
        return sOAWALPIUTANG;
    }

    public void setSOAWALPIUTANG(Integer sOAWALPIUTANG) {
        this.sOAWALPIUTANG = sOAWALPIUTANG;
    }

    public String getEMAIL() {
        return eMAIL;
    }

    public void setEMAIL(String eMAIL) {
        this.eMAIL = eMAIL;
    }

    public String getCPERSON() {
        return cPERSON;
    }

    public void setCPERSON(String cPERSON) {
        this.cPERSON = cPERSON;
    }

    public String getTGLLHR() {
        return tGLLHR;
    }

    public void setTGLLHR(String tGLLHR) {
        this.tGLLHR = tGLLHR;
    }

    public String getHP() {
        return hP;
    }

    public void setHP(String hP) {
        this.hP = hP;
    }

    public String getJNSKELAMIN() {
        return jNSKELAMIN;
    }

    public void setJNSKELAMIN(String jNSKELAMIN) {
        this.jNSKELAMIN = jNSKELAMIN;
    }

    public String getLEVELS() {
        return lEVELS;
    }

    public void setLEVELS(String lEVELS) {
        this.lEVELS = lEVELS;
    }

}
