package com.hackaton.bri.ragatee.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Transaksi {

    @Expose
    @SerializedName("timestamp")
    private String timestamp;
    @Expose
    @SerializedName("invoiceCode")
    private String invoicecode;
    @Expose
    @SerializedName("fund")
    private int fund;
    @Expose
    @SerializedName("crowdfundingId")
    private String crowdfundingid;
    @Expose
    @SerializedName("_id")
    private String Id;
    @Expose
    @SerializedName("fundingStatus")
    private String fundingstatus;
    @Expose
    @SerializedName("image")
    private String image;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("dailyReports")
    private ArrayList<Report> dailyReports;

    public Transaksi() { }

    public Transaksi(String timestamp, String invoicecode, int fund, String crowdfundingid, String id, String fundingstatus, String image, String title) {
        this.timestamp = timestamp;
        this.invoicecode = invoicecode;
        this.fund = fund;
        this.crowdfundingid = crowdfundingid;
        Id = id;
        this.fundingstatus = fundingstatus;
        this.image = image;
        this.title = title;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getInvoicecode() {
        return invoicecode;
    }

    public int getFund() {
        return fund;
    }

    public String getCrowdfundingid() {
        return crowdfundingid;
    }

    public String getId() {
        return Id;
    }

    public String getFundingstatus() {
        return fundingstatus;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Report> getDailyReports() {
        return dailyReports;
    }
}
