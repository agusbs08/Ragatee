package com.hackaton.bri.ragatee.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Report implements Serializable {

    @Expose
    @SerializedName("_id")
    private String id;

    @Expose
    @SerializedName("time")
    private Long time;

    @Expose
    @SerializedName("description")
    private String description;

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("image")
    private String image;

    public Report() {
    }

    public Report(String id, Long time, String description, String title, String image) {
        this.id = id;
        this.time = time;
        this.description = description;
        this.title = title;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public Long getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }
}
