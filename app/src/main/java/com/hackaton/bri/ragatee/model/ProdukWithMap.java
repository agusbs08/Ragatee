package com.hackaton.bri.ragatee.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProdukWithMap {

    @Expose
    @SerializedName("lng")
    private double lng;
    @Expose
    @SerializedName("lat")
    private double lat;
    @Expose
    @SerializedName("distance")
    private double distance;
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
    @SerializedName("image")
    private String image;
    @Expose
    @SerializedName("_id")
    private String Id;

    public double getLng() {
        return lng;
    }

    public double getLat() {
        return lat;
    }

    public double getDistance() {
        return distance;
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

    public String getImage() {
        return image;
    }

    public String getId() {
        return Id;
    }
}
