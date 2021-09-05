package com.mss.qurirq.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nik")
    @Expose
    private String nik;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("tanggal_lahir")
    @Expose
    private String tanggalLahir;
    @SerializedName("no_telp")
    @Expose
    private String noTelp;
    @SerializedName("kd_outlet")
    @Expose
    private String kdOutlet;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("foto_ktp")
    @Expose
    private String fotoKtp;
    @SerializedName("otoritas")
    @Expose
    private String otoritas;
    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("firebase_token")
    @Expose
    private String firebaseToken;
    @SerializedName("email_activation")
    @Expose
    private String emailActivation;
    @SerializedName("activation_token")
    @Expose
    private String activationToken;
    @SerializedName("kd_peg")
    @Expose
    private String kdPeg;
    @SerializedName("kd_alamat")
    @Expose
    private String kdAlamat;
    @SerializedName("id_user")
    @Expose
    private String idUser;
    @SerializedName("nama_penerima")
    @Expose
    private String namaPenerima;
    @SerializedName("provinsi")
    @Expose
    private String provinsi;
    @SerializedName("kota")
    @Expose
    private String kota;
    @SerializedName("kecamatan")
    @Expose
    private String kecamatan;
    @SerializedName("kd_provinsi")
    @Expose
    private String kdProvinsi;
    @SerializedName("kd_kota")
    @Expose
    private String kdKota;
    @SerializedName("kd_kecamatan")
    @Expose
    private String kdKecamatan;
    @SerializedName("alamat_lengkap")
    @Expose
    private String alamatLengkap;
    @SerializedName("no_telp_penerima")
    @Expose
    private String noTelpPenerima;
    @SerializedName("kode_pos")
    @Expose
    private String kodePos;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("KD_CUST")
    @Expose
    private String kDCUST;
    @SerializedName("KATEGORI")
    @Expose
    private String kATEGORI;
    @SerializedName("NM_CUST")
    @Expose
    private String nMCUST;
    @SerializedName("NIK")
    @Expose
    private String nIK;
    @SerializedName("ALM_CUST")
    @Expose
    private String aLMCUST;
    @SerializedName("TELP1")
    @Expose
    private String tELP1;
    @SerializedName("TELP2")
    @Expose
    private String tELP2;
    @SerializedName("JML_PIUTANG")
    @Expose
    private String jMLPIUTANG;
    @SerializedName("JML_BAYAR")
    @Expose
    private String jMLBAYAR;
    @SerializedName("TGL_LIMIT")
    @Expose
    private String tGLLIMIT;
    @SerializedName("NPWP")
    @Expose
    private String nPWP;
    @SerializedName("NM_PKP")
    @Expose
    private String nMPKP;
    @SerializedName("E_MAIL")
    @Expose
    private String eMAIL;
    @SerializedName("BANK1")
    @Expose
    private String bANK1;
    @SerializedName("BANK2")
    @Expose
    private String bANK2;
    @SerializedName("POINT_JL")
    @Expose
    private String pOINTJL;
    @SerializedName("POINT_GUNA")
    @Expose
    private String pOINTGUNA;
    @SerializedName("TGL_LHR")
    @Expose
    private String tGLLHR;
    @SerializedName("STS_MEMBER")
    @Expose
    private String sTSMEMBER;
    @SerializedName("KD_WIL")
    @Expose
    private String kDWIL;
    @SerializedName("WILAYAH")
    @Expose
    private String wILAYAH;
    @SerializedName("HP")
    @Expose
    private String hP;
    @SerializedName("JNS_KELAMIN")
    @Expose
    private String jNSKELAMIN;
    @SerializedName("KD_PEG")
    @Expose
    private String kDPEG;
    @SerializedName("TOP")
    @Expose
    private Integer tOP;
    @SerializedName("POINT_BL_INI")
    @Expose
    private Integer pOINTBLINI;
    @SerializedName("TGL_TUKAR_POINT")
    @Expose
    private String tGLTUKARPOINT;
    @SerializedName("POINT_LALU")
    @Expose
    private String pOINTLALU;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getKdOutlet() {
        return kdOutlet;
    }

    public void setKdOutlet(String kdOutlet) {
        this.kdOutlet = kdOutlet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFotoKtp() {
        return fotoKtp;
    }

    public void setFotoKtp(String fotoKtp) {
        this.fotoKtp = fotoKtp;
    }

    public String getOtoritas() {
        return otoritas;
    }

    public void setOtoritas(String otoritas) {
        this.otoritas = otoritas;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public String getEmailActivation() {
        return emailActivation;
    }

    public void setEmailActivation(String emailActivation) {
        this.emailActivation = emailActivation;
    }

    public String getActivationToken() {
        return activationToken;
    }

    public void setActivationToken(String activationToken) {
        this.activationToken = activationToken;
    }

    public String getKdPeg() {
        return kdPeg;
    }

    public void setKdPeg(String kdPeg) {
        this.kdPeg = kdPeg;
    }

    public String getKdAlamat() {
        return kdAlamat;
    }

    public void setKdAlamat(String kdAlamat) {
        this.kdAlamat = kdAlamat;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNamaPenerima() {
        return namaPenerima;
    }

    public void setNamaPenerima(String namaPenerima) {
        this.namaPenerima = namaPenerima;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getKdProvinsi() {
        return kdProvinsi;
    }

    public void setKdProvinsi(String kdProvinsi) {
        this.kdProvinsi = kdProvinsi;
    }

    public String getKdKota() {
        return kdKota;
    }

    public void setKdKota(String kdKota) {
        this.kdKota = kdKota;
    }

    public String getKdKecamatan() {
        return kdKecamatan;
    }

    public void setKdKecamatan(String kdKecamatan) {
        this.kdKecamatan = kdKecamatan;
    }

    public String getAlamatLengkap() {
        return alamatLengkap;
    }

    public void setAlamatLengkap(String alamatLengkap) {
        this.alamatLengkap = alamatLengkap;
    }

    public String getNoTelpPenerima() {
        return noTelpPenerima;
    }

    public void setNoTelpPenerima(String noTelpPenerima) {
        this.noTelpPenerima = noTelpPenerima;
    }

    public String getKodePos() {
        return kodePos;
    }

    public void setKodePos(String kodePos) {
        this.kodePos = kodePos;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getKDCUST() {
        return kDCUST;
    }

    public void setKDCUST(String kDCUST) {
        this.kDCUST = kDCUST;
    }

    public String getKATEGORI() {
        return kATEGORI;
    }

    public void setKATEGORI(String kATEGORI) {
        this.kATEGORI = kATEGORI;
    }

    public String getNMCUST() {
        return nMCUST;
    }

    public void setNMCUST(String nMCUST) {
        this.nMCUST = nMCUST;
    }

    public String getNIK() {
        return nIK;
    }

    public void setNIK(String nIK) {
        this.nIK = nIK;
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

    public String getJMLPIUTANG() {
        return jMLPIUTANG;
    }

    public void setJMLPIUTANG(String jMLPIUTANG) {
        this.jMLPIUTANG = jMLPIUTANG;
    }

    public String getJMLBAYAR() {
        return jMLBAYAR;
    }

    public void setJMLBAYAR(String jMLBAYAR) {
        this.jMLBAYAR = jMLBAYAR;
    }

    public String getTGLLIMIT() {
        return tGLLIMIT;
    }

    public void setTGLLIMIT(String tGLLIMIT) {
        this.tGLLIMIT = tGLLIMIT;
    }

    public String getNPWP() {
        return nPWP;
    }

    public void setNPWP(String nPWP) {
        this.nPWP = nPWP;
    }

    public String getNMPKP() {
        return nMPKP;
    }

    public void setNMPKP(String nMPKP) {
        this.nMPKP = nMPKP;
    }

    public String getEMAIL() {
        return eMAIL;
    }

    public void setEMAIL(String eMAIL) {
        this.eMAIL = eMAIL;
    }

    public String getBANK1() {
        return bANK1;
    }

    public void setBANK1(String bANK1) {
        this.bANK1 = bANK1;
    }

    public String getBANK2() {
        return bANK2;
    }

    public void setBANK2(String bANK2) {
        this.bANK2 = bANK2;
    }

    public String getPOINTJL() {
        return pOINTJL;
    }

    public void setPOINTJL(String pOINTJL) {
        this.pOINTJL = pOINTJL;
    }

    public String getPOINTGUNA() {
        return pOINTGUNA;
    }

    public void setPOINTGUNA(String pOINTGUNA) {
        this.pOINTGUNA = pOINTGUNA;
    }

    public String getTGLLHR() {
        return tGLLHR;
    }

    public void setTGLLHR(String tGLLHR) {
        this.tGLLHR = tGLLHR;
    }

    public String getSTSMEMBER() {
        return sTSMEMBER;
    }

    public void setSTSMEMBER(String sTSMEMBER) {
        this.sTSMEMBER = sTSMEMBER;
    }

    public String getKDWIL() {
        return kDWIL;
    }

    public void setKDWIL(String kDWIL) {
        this.kDWIL = kDWIL;
    }

    public String getWILAYAH() {
        return wILAYAH;
    }

    public void setWILAYAH(String wILAYAH) {
        this.wILAYAH = wILAYAH;
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

    public String getKDPEG() {
        return kDPEG;
    }

    public void setKDPEG(String kDPEG) {
        this.kDPEG = kDPEG;
    }

    public Integer getTOP() {
        return tOP;
    }

    public void setTOP(Integer tOP) {
        this.tOP = tOP;
    }

    public Integer getPOINTBLINI() {
        return pOINTBLINI;
    }

    public void setPOINTBLINI(Integer pOINTBLINI) {
        this.pOINTBLINI = pOINTBLINI;
    }

    public String getTGLTUKARPOINT() {
        return tGLTUKARPOINT;
    }

    public void setTGLTUKARPOINT(String tGLTUKARPOINT) {
        this.tGLTUKARPOINT = tGLTUKARPOINT;
    }

    public String getPOINTLALU() {
        return pOINTLALU;
    }

    public void setPOINTLALU(String pOINTLALU) {
        this.pOINTLALU = pOINTLALU;
    }

}
