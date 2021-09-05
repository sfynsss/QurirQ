package com.mss.qurirq.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterBeli {

    @SerializedName("NO_ENT")
    @Expose
    private String nOENT;
    @SerializedName("id_toko")
    @Expose
    private Integer idToko;
    @SerializedName("TANGGAL")
    @Expose
    private String tANGGAL;
    @SerializedName("NO_ENT_ORD")
    @Expose
    private String nOENTORD;
    @SerializedName("KD_SUPPL")
    @Expose
    private String kDSUPPL;
    @SerializedName("TOTAL")
    @Expose
    private Double tOTAL;
    @SerializedName("disc1")
    @Expose
    private Double disc1;
    @SerializedName("disc2")
    @Expose
    private Double disc2;
    @SerializedName("disc_rp")
    @Expose
    private Double discRp;
    @SerializedName("PPN")
    @Expose
    private Double pPN;
    @SerializedName("JML_BAYAR")
    @Expose
    private Double jMLBAYAR;
    @SerializedName("JNS_BYR")
    @Expose
    private String jNSBYR;
    @SerializedName("NETTO")
    @Expose
    private Double nETTO;
    @SerializedName("TOT_DISC1")
    @Expose
    private Double tOTDISC1;
    @SerializedName("TOT_DISC2")
    @Expose
    private Double tOTDISC2;
    @SerializedName("TOT_BONUS")
    @Expose
    private Double tOTBONUS;
    @SerializedName("TOP")
    @Expose
    private Integer tOP;
    @SerializedName("U_MUKA")
    @Expose
    private Double uMUKA;
    @SerializedName("JML_RETUR")
    @Expose
    private Double jMLRETUR;
    @SerializedName("STS_POST")
    @Expose
    private String sTSPOST;
    @SerializedName("TGL_POST")
    @Expose
    private String tGLPOST;
    @SerializedName("KD_USER")
    @Expose
    private String kDUSER;
    @SerializedName("JNS_SEDIA")
    @Expose
    private String jNSSEDIA;
    @SerializedName("STS_KAS")
    @Expose
    private Integer sTSKAS;
    @SerializedName("STS_SO_AWAL")
    @Expose
    private Integer sTSSOAWAL;
    @SerializedName("TOT_BERAT")
    @Expose
    private Double tOTBERAT;
    @SerializedName("TGL_CETAK")
    @Expose
    private String tGLCETAK;
    @SerializedName("JAM_CETAK")
    @Expose
    private String jAMCETAK;
    @SerializedName("CETAKAN_KE")
    @Expose
    private Integer cETAKANKE;
    @SerializedName("TGL_ORDER")
    @Expose
    private String tGLORDER;
    @SerializedName("NM_SUPPL")
    @Expose
    private String nMSUPPL;

    public String getnOENT() {
        return nOENT;
    }

    public void setnOENT(String nOENT) {
        this.nOENT = nOENT;
    }

    public Integer getIdToko() {
        return idToko;
    }

    public void setIdToko(Integer idToko) {
        this.idToko = idToko;
    }

    public String gettANGGAL() {
        return tANGGAL;
    }

    public void settANGGAL(String tANGGAL) {
        this.tANGGAL = tANGGAL;
    }

    public String getnOENTORD() {
        return nOENTORD;
    }

    public void setnOENTORD(String nOENTORD) {
        this.nOENTORD = nOENTORD;
    }

    public String getkDSUPPL() {
        return kDSUPPL;
    }

    public void setkDSUPPL(String kDSUPPL) {
        this.kDSUPPL = kDSUPPL;
    }

    public Double gettOTAL() {
        return tOTAL;
    }

    public void settOTAL(Double tOTAL) {
        this.tOTAL = tOTAL;
    }

    public Double getDisc1() {
        return disc1;
    }

    public void setDisc1(Double disc1) {
        this.disc1 = disc1;
    }

    public Double getDisc2() {
        return disc2;
    }

    public void setDisc2(Double disc2) {
        this.disc2 = disc2;
    }

    public Double getDiscRp() {
        return discRp;
    }

    public void setDiscRp(Double discRp) {
        this.discRp = discRp;
    }

    public Double getpPN() {
        return pPN;
    }

    public void setpPN(Double pPN) {
        this.pPN = pPN;
    }

    public Double getjMLBAYAR() {
        return jMLBAYAR;
    }

    public void setjMLBAYAR(Double jMLBAYAR) {
        this.jMLBAYAR = jMLBAYAR;
    }

    public String getjNSBYR() {
        return jNSBYR;
    }

    public void setjNSBYR(String jNSBYR) {
        this.jNSBYR = jNSBYR;
    }

    public Double getnETTO() {
        return nETTO;
    }

    public void setnETTO(Double nETTO) {
        this.nETTO = nETTO;
    }

    public Double gettOTDISC1() {
        return tOTDISC1;
    }

    public void settOTDISC1(Double tOTDISC1) {
        this.tOTDISC1 = tOTDISC1;
    }

    public Double gettOTDISC2() {
        return tOTDISC2;
    }

    public void settOTDISC2(Double tOTDISC2) {
        this.tOTDISC2 = tOTDISC2;
    }

    public Double gettOTBONUS() {
        return tOTBONUS;
    }

    public void settOTBONUS(Double tOTBONUS) {
        this.tOTBONUS = tOTBONUS;
    }

    public Integer gettOP() {
        return tOP;
    }

    public void settOP(Integer tOP) {
        this.tOP = tOP;
    }

    public Double getuMUKA() {
        return uMUKA;
    }

    public void setuMUKA(Double uMUKA) {
        this.uMUKA = uMUKA;
    }

    public Double getjMLRETUR() {
        return jMLRETUR;
    }

    public void setjMLRETUR(Double jMLRETUR) {
        this.jMLRETUR = jMLRETUR;
    }

    public String getsTSPOST() {
        return sTSPOST;
    }

    public void setsTSPOST(String sTSPOST) {
        this.sTSPOST = sTSPOST;
    }

    public String gettGLPOST() {
        return tGLPOST;
    }

    public void settGLPOST(String tGLPOST) {
        this.tGLPOST = tGLPOST;
    }

    public String getkDUSER() {
        return kDUSER;
    }

    public void setkDUSER(String kDUSER) {
        this.kDUSER = kDUSER;
    }

    public String getjNSSEDIA() {
        return jNSSEDIA;
    }

    public void setjNSSEDIA(String jNSSEDIA) {
        this.jNSSEDIA = jNSSEDIA;
    }

    public Integer getsTSKAS() {
        return sTSKAS;
    }

    public void setsTSKAS(Integer sTSKAS) {
        this.sTSKAS = sTSKAS;
    }

    public Integer getsTSSOAWAL() {
        return sTSSOAWAL;
    }

    public void setsTSSOAWAL(Integer sTSSOAWAL) {
        this.sTSSOAWAL = sTSSOAWAL;
    }

    public Double gettOTBERAT() {
        return tOTBERAT;
    }

    public void settOTBERAT(Double tOTBERAT) {
        this.tOTBERAT = tOTBERAT;
    }

    public String gettGLCETAK() {
        return tGLCETAK;
    }

    public void settGLCETAK(String tGLCETAK) {
        this.tGLCETAK = tGLCETAK;
    }

    public String getjAMCETAK() {
        return jAMCETAK;
    }

    public void setjAMCETAK(String jAMCETAK) {
        this.jAMCETAK = jAMCETAK;
    }

    public Integer getcETAKANKE() {
        return cETAKANKE;
    }

    public void setcETAKANKE(Integer cETAKANKE) {
        this.cETAKANKE = cETAKANKE;
    }

    public String gettGLORDER() {
        return tGLORDER;
    }

    public void settGLORDER(String tGLORDER) {
        this.tGLORDER = tGLORDER;
    }

    public String getnMSUPPL() {
        return nMSUPPL;
    }

    public void setnMSUPPL(String nMSUPPL) {
        this.nMSUPPL = nMSUPPL;
    }
}
