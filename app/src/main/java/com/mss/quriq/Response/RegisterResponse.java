package com.mss.quriq.Response;

import com.mss.quriq.Table.Register;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

    @SerializedName("register")
    @Expose
    private Register register;

    public Register getRegister() {
        return register;
    }

    public void setRegister(Register register) {
        this.register = register;
    }

}