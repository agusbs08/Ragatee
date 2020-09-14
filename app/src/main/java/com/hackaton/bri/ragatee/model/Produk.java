package com.hackaton.bri.ragatee.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Produk {

    @Expose
    @SerializedName("rating")
    private String rating;
    @Expose
    @SerializedName("endDay")
    private Long timestamp;
    @Expose
    @SerializedName("fundingProgressPercentage")
    private double fundingprogresspercentage;
    @Expose
    @SerializedName("fundedStatus")
    private boolean fundedstatus;
    @Expose
    @SerializedName("fundNeeded")
    private int fundneeded;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("image")
    private String image;
    @Expose
    @SerializedName("id")
    private String Id;

    public Produk() {
    }

    public Produk(int fundingprogresspercentage, boolean fundedstatus, int fundneeded, String title, String image, String id) {
        this.fundingprogresspercentage = fundingprogresspercentage;
        this.fundedstatus = fundedstatus;
        this.fundneeded = fundneeded;
        this.title = title;
        this.image = image;
        Id = id;
    }

    public double getFundingprogresspercentage() {
        return fundingprogresspercentage;
    }

    public boolean getFundedstatus() {
        return fundedstatus;
    }

    public int getFundneeded() {
        return fundneeded;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return Id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getImage() {
        return image;
    }

    public String getRating() {
        return rating;
    }
}
