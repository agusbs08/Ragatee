package com.hackaton.bri.ragatee.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Images {

    @Expose
    @SerializedName("url")
    private String url;
    @Expose
    @SerializedName("_id")
    private String Id;

    public Images() {
    }

    public Images(String url, String id) {
        this.url = url;
        Id = id;
    }

    public String getUrl() {
        return url;
    }

    public String getId() {
        return Id;
    }
}
