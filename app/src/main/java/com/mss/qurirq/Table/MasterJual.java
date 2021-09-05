package com.mss.qurirq.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterJual {

    @SerializedName("NO_ENT")
    @Expose
    private String nOENT;
    @SerializedName("ID_TOKO")
    @Expose
    private Integer iDTOKO;
    @SerializedName("TANGGAL")
    @Expose
    private String tANGGAL;
    @SerializedName("NO_ENT_ORD")
    @Expose
    private String nOENTORD;
    @SerializedName("KD_CUST")
    @Expose
    private String kDCUST;
    @SerializedName("TOTAL")
    @Expose
    private Double tOTAL;
    @SerializedName("DISC_PR")
    @Expose
    private Double dISCPR;
    @SerializedName("DISC_VALUE")
    @Expose
    private Double dISCVALUE;
    @SerializedName("U_MUKA")
    @Expose
    private Double uMUKA;
    @SerializedName("JML_BAYAR")
    @Expose
    private Double jMLBAYAR;
    @SerializedName("JML_RETUR")
    @Expose
    private Double jMLRETUR;
    @SerializedName("JNS_BYR")
    @Expose
    private String jNSBYR;
    @SerializedName("PPN")
    @Expose
    private Double pPN;
    @SerializedName("TOT_BONUS")
    @Expose
    private Double tOTBONUS;
    @SerializedName("TOT_DISC1")
    @Expose
    private Double tOTDISC1;
    @SerializedName("TOT_DISC2")
    @Expose
    private Double tOTDISC2;
    @SerializedName("NETTO")
    @Expose
    private Double nETTO;
    @SerializedName("TOP_PROS")
    @Expose
    private Double tOPPROS;
    @SerializedName("TOP_PROS_HARI")
    @Expose
    private Integer tOPPROSHARI;
    @SerializedName("TOP")
    @Expose
    private Integer tOP;
    @SerializedName("TOT_DISC_RP")
    @Expose
    private Double tOTDISCRP;
    @SerializedName("POT1")
    @Expose
    private Double pOT1;
    @SerializedName("PPN_RP")
    @Expose
    private Double pPNRP;
    @SerializedName("STS_POST")
    @Expose
    private String sTSPOST;
    @SerializedName("TGL_POST")
    @Expose
    private String tGLPOST;
    @SerializedName("KD_USER")
    @Expose
    private String kDUSER;
    @SerializedName("STS_KAS")
    @Expose
    private Integer sTSKAS;
    @SerializedName("STS_SO_AWAL")
    @Expose
    private Integer sTSSOAWAL;
    @SerializedName("STS_PENDING")
    @Expose
    private String sTSPENDING;
    @SerializedName("STS_RETUR")
    @Expose
    private String sTSRETUR;
    @SerializedName("STS_SIMPAN")
    @Expose
    private String sTSSIMPAN;

    public String getnOENT() {
        return nOENT;
    }

    public void setnOENT(String nOENT) {
        this.nOENT = nOENT;
    }

    public Integer getiDTOKO() {
        return iDTOKO;
    }

    public void setiDTOKO(Integer iDTOKO) {
        this.iDTOKO = iDTOKO;
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

    public String getkDCUST() {
        return kDCUST;
    }

    public void setkDCUST(String kDCUST) {
        this.kDCUST = kDCUST;
    }

    public Double gettOTAL() {
        return tOTAL;
    }

    public void settOTAL(Double tOTAL) {
        this.tOTAL = tOTAL;
    }

    public Double getdISCPR() {
        return dISCPR;
    }

    public void setdISCPR(Double dISCPR) {
        this.dISCPR = dISCPR;
    }

    public Double getdISCVALUE() {
        return dISCVALUE;
    }

    public void setdISCVALUE(Double dISCVALUE) {
        this.dISCVALUE = dISCVALUE;
    }

    public Double getuMUKA() {
        return uMUKA;
    }

    public void setuMUKA(Double uMUKA) {
        this.uMUKA = uMUKA;
    }

    public Double getjMLBAYAR() {
        return jMLBAYAR;
    }

    public void setjMLBAYAR(Double jMLBAYAR) {
        this.jMLBAYAR = jMLBAYAR;
    }

    public Double getjMLRETUR() {
        return jMLRETUR;
    }

    public void setjMLRETUR(Double jMLRETUR) {
        this.jMLRETUR = jMLRETUR;
    }

    public String getjNSBYR() {
        return jNSBYR;
    }

    public void setjNSBYR(String jNSBYR) {
        this.jNSBYR = jNSBYR;
    }

    public Double getpPN() {
        return pPN;
    }

    public void setpPN(Double pPN) {
        this.pPN = pPN;
    }

    public Double gettOTBONUS() {
        return tOTBONUS;
    }

    public void settOTBONUS(Double tOTBONUS) {
        this.tOTBONUS = tOTBONUS;
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

    public Double getnETTO() {
        return nETTO;
    }

    public void setnETTO(Double nETTO) {
        this.nETTO = nETTO;
    }

    public Double gettOPPROS() {
        return tOPPROS;
    }

    public void settOPPROS(Double tOPPROS) {
        this.tOPPROS = tOPPROS;
    }

    public Integer gettOPPROSHARI() {
        return tOPPROSHARI;
    }

    public void settOPPROSHARI(Integer tOPPROSHARI) {
        this.tOPPROSHARI = tOPPROSHARI;
    }

    public Integer gettOP() {
        return tOP;
    }

    public void settOP(Integer tOP) {
        this.tOP = tOP;
    }

    public Double gettOTDISCRP() {
        return tOTDISCRP;
    }

    public void settOTDISCRP(Double tOTDISCRP) {
        this.tOTDISCRP = tOTDISCRP;
    }

    public Double getpOT1() {
        return pOT1;
    }

    public void setpOT1(Double pOT1) {
        this.pOT1 = pOT1;
    }

    public Double getpPNRP() {
        return pPNRP;
    }

    public void setpPNRP(Double pPNRP) {
        this.pPNRP = pPNRP;
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

    public String getsTSPENDING() {
        return sTSPENDING;
    }

    public void setsTSPENDING(String sTSPENDING) {
        this.sTSPENDING = sTSPENDING;
    }

    public String getsTSRETUR() {
        return sTSRETUR;
    }

    public void setsTSRETUR(String sTSRETUR) {
        this.sTSRETUR = sTSRETUR;
    }

    public String getsTSSIMPAN() {
        return sTSSIMPAN;
    }

    public void setsTSSIMPAN(String sTSSIMPAN) {
        this.sTSSIMPAN = sTSSIMPAN;
    }
}
