package com.android.apartmentmanagementsystem.model;

import com.google.gson.annotations.SerializedName;

public class Complain {
    @SerializedName("cell")
    private String cell;
    @SerializedName("complain")
    private String complain;
    @SerializedName("date")
    private String date;
    @SerializedName("time")
    private String time;

    public String getCell() {
        return cell;
    }

    public String getComplain() {
        return complain;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
