package com.hackaton.bri.ragatee.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegistrationRequest {

    @Expose
    @SerializedName("accountNumber")
    private String accountnumber;
    @Expose
    @SerializedName("address")
    private String address;
    @Expose
    @SerializedName("phone")
    private String phone;
    @Expose
    @SerializedName("npwpNo")
    private String npwpno;
    @Expose
    @SerializedName("ktpNo")
    private String ktpno;
    @Expose
    @SerializedName("id")
    private String id;

    public RegistrationRequest() {
    }

    public RegistrationRequest(String accountnumber, String address, String phone, String npwpno, String ktpno, String id) {
        this.accountnumber = accountnumber;
        this.address = address;
        this.phone = phone;
        this.npwpno = npwpno;
        this.ktpno = ktpno;
        this.id = id;
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getNpwpno() {
        return npwpno;
    }

    public String getKtpno() {
        return ktpno;
    }

    public String getId() {
        return id;
    }
}
