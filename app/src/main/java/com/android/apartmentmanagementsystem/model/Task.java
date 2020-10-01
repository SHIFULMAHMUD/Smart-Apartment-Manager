package com.android.apartmentmanagementsystem.model;

import com.google.gson.annotations.SerializedName;

public class Task {
    @SerializedName("cell")
    private String cell;
    @SerializedName("id")
    private String id;
    @SerializedName("guard_name")
    private String guard_name;
    @SerializedName("task")
    private String task;
    @SerializedName("date")
    private String date;
    @SerializedName("time")
    private String time;
    @SerializedName("renter_name")
    private String renter_name;
    @SerializedName("flat_no")
    private String flat_no;
    @SerializedName("floor_no")
    private String floor_no;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String massage;
    @SerializedName("status")
    private String status;

    public String getCell() {
        return cell;
    }

    public String getGuard_name() {
        return guard_name;
    }

    public String getTask() {
        return task;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getRenter_name() {
        return renter_name;
    }

    public String getFlat_no() {
        return flat_no;
    }

    public String getFloor_no() {
        return floor_no;
    }

    public String getValue() {
        return value;
    }

    public String getMassage() {
        return massage;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
