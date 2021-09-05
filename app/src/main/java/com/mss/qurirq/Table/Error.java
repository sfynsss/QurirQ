package com.mss.qurirq.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Error {

    @SerializedName("email")
    @Expose
    private List<String> email = null;
    @SerializedName("no_telp")
    @Expose
    private List<String> noTelp = null;

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public List<String> getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(List<String> noTelp) {
        this.noTelp = noTelp;
    }

}