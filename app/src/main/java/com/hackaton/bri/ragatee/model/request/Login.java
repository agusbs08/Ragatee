package com.hackaton.bri.ragatee.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {

    @Expose
    @SerializedName("profilePicture")
    private String profilepicture;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("userType")
    private String usertype;
    @Expose
    @SerializedName("email")
    private String email;

    public Login(String profilepicture, String name, String usertype, String email) {
        this.profilepicture = profilepicture;
        this.name = name;
        this.usertype = usertype;
        this.email = email;
    }

    public String getProfilepicture() {
        return profilepicture;
    }

    public String getName() {
        return name;
    }

    public String getUsertype() {
        return usertype;
    }

    public String getEmail() {
        return email;
    }
}
