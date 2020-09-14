package com.hackaton.bri.ragatee.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Payment {

    @Expose
    @SerializedName("userId")
    private String userId;

    public Payment(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
