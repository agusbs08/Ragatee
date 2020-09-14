package com.hackaton.bri.ragatee.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    @Expose
    @SerializedName("accountBalance")
    private String accountbalance;
    @Expose
    @SerializedName("phone")
    private String phone;
    @Expose
    @SerializedName("npwpNo")
    private String npwpno;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("ktpNo")
    private String ktpno;
    @Expose
    @SerializedName("address")
    private String address;
    @Expose
    @SerializedName("accountNumber")
    private String accountnumber;
    @Expose
    @SerializedName("verified")
    private boolean verified;
    @Expose
    @SerializedName("email")
    private String email;
    @Expose
    @SerializedName("profilePicture")
    private String profilepicture;
    @Expose
    @SerializedName("_id")
    private String Id;

    public String getAccountbalance() {
        return accountbalance;
    }

    public String getPhone() {
        return phone;
    }

    public String getNpwpno() {
        return npwpno;
    }

    public String getName() {
        return name;
    }

    public String getKtpno() {
        return ktpno;
    }

    public String getAddress() {
        return address;
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public boolean getVerified() {
        return verified;
    }

    public String getEmail() {
        return email;
    }

    public String getProfilepicture() {
        return profilepicture;
    }

    public String getId() {
        return Id;
    }
}
