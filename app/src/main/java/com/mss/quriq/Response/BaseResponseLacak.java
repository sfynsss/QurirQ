package com.mss.quriq.Response;

import com.mss.quriq.Table.Lacak;
import com.mss.quriq.Table.Manifest;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseResponseLacak {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("lacak")
    @Expose
    private Lacak lacak;
    @SerializedName("manifest")
    @Expose
    private List<Manifest> manifest = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Lacak getLacak() {
        return lacak;
    }

    public void setLacak(Lacak lacak) {
        this.lacak = lacak;
    }

    public List<Manifest> getManifest() {
        return manifest;
    }

    public void setManifest(List<Manifest> manifest) {
        this.manifest = manifest;
    }

}
