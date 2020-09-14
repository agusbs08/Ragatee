package com.hackaton.bri.ragatee.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailProduk {

    @Expose
    @SerializedName("campaigner")
    private String campaigner;
    @Expose
    @SerializedName("rating")
    private int rating;
    @Expose
    @SerializedName("proposalUrl")
    private String proposalUrl;
    @Expose
    @SerializedName("ROI")
    private int ROI;
    @Expose
    @SerializedName("endDay")
    private Long endDay;
    @Expose
    @SerializedName("lng")
    private double lng;
    @Expose
    @SerializedName("lat")
    private double lat;
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("fundedStatus")
    private boolean fundedstatus;
    @Expose
    @SerializedName("fundCollected")
    private int fundcollected;
    @Expose
    @SerializedName("fundNeeded")
    private int fundneeded;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("images")
    private List<Images> images;
    @Expose
    @SerializedName("_id")
    private String Id;

    public DetailProduk() {

    }

    public DetailProduk(String proposalUrl, int ROI, Long endDay, double lng, double lat, String description, boolean fundedstatus, int fundcollected, int fundneeded, String title, List<Images> images, String id) {
        this.proposalUrl = proposalUrl;
        this.ROI = ROI;
        this.endDay = endDay;
        this.lng = lng;
        this.lat = lat;
        this.description = description;
        this.fundedstatus = fundedstatus;
        this.fundcollected = fundcollected;
        this.fundneeded = fundneeded;
        this.title = title;
        this.images = images;
        Id = id;
    }

    public double getLng() {
        return lng;
    }

    public double getLat() {
        return lat;
    }

    public String getDescription() {
        return description;
    }

    public boolean isFundedstatus() {
        return fundedstatus;
    }

    public int getFundcollected() {
        return fundcollected;
    }

    public int getFundneeded() {
        return fundneeded;
    }

    public String getTitle() {
        return title;
    }

    public List<Images> getImages() {
        return images;
    }

    public String getId() {
        return Id;
    }

    public String getProposalUrl() {
        return proposalUrl;
    }

    public int getROI() {
        return ROI;
    }

    public Long getEndDay() {
        return endDay;
    }

    public int getRating() {
        return rating;
    }

    public String getCampaigner() {
        return campaigner;
    }
}
