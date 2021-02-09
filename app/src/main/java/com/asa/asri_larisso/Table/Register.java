package com.asa.asri_larisso.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Register {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("no_telp")
    @Expose
    private String noTelp;
    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("otoritas")
    @Expose
    private String otoritas;
    @SerializedName("JNS_KELAMIN")
    @Expose
    private String jNSKELAMIN;
    @SerializedName("KD_CUST")
    @Expose
    private String kDCUST;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOtoritas() {
        return otoritas;
    }

    public void setOtoritas(String otoritas) {
        this.otoritas = otoritas;
    }

    public String getJNSKELAMIN() {
        return jNSKELAMIN;
    }

    public void setJNSKELAMIN(String jNSKELAMIN) {
        this.jNSKELAMIN = jNSKELAMIN;
    }

    public String getKDCUST() {
        return kDCUST;
    }

    public void setKDCUST(String kDCUST) {
        this.kDCUST = kDCUST;
    }

}
