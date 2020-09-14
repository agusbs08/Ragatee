package com.hackaton.bri.ragatee.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cart {

    @Expose
    @SerializedName("fundRemaining")
    private int fundremaining;
    @Expose
    @SerializedName("fundProgressPercentage")
    private double fundprogresspercentage;
    @Expose
    @SerializedName("fund")
    private int fund;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("image")
    private String image;
    @SerializedName("cartItemId")
    private String cartItemId;

    public Cart() {

    }

    public Cart(int fundremaining, double fundprogresspercentage, int fund, String title, String image, String cartItemId) {
        this.fundremaining = fundremaining;
        this.fundprogresspercentage = fundprogresspercentage;
        this.fund = fund;
        this.title = title;
        this.image = image;
        this.cartItemId = cartItemId;
    }

    public int getFundremaining() {
        return fundremaining;
    }

    public double getFundprogresspercentage() {
        return fundprogresspercentage;
    }

    public int getFund() {
        return fund;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getCartItemId() {
        return cartItemId;
    }
}
