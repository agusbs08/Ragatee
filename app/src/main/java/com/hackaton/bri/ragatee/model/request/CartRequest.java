package com.hackaton.bri.ragatee.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartRequest {

    @Expose
    @SerializedName("fund")
    private int fund;
    @Expose
    @SerializedName("crowdfundingId")
    private String crowdfundingid;
    @Expose
    @SerializedName("userId")
    private String userid;

    public CartRequest() {
    }

    public CartRequest(int fund, String crowdfundingid, String userid) {
        this.fund = fund;
        this.crowdfundingid = crowdfundingid;
        this.userid = userid;
    }

    public int getFund() {
        return fund;
    }

    public String getCrowdfundingid() {
        return crowdfundingid;
    }

    public String getUserid() {
        return userid;
    }
}
