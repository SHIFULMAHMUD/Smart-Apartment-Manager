package com.android.apartmentmanagementsystem.model;

import com.google.gson.annotations.SerializedName;

public class Guest {
    @SerializedName("guest_name")
    private String guest_name;
    @SerializedName("guest_cell")
    private String guest_cell;
    @SerializedName("total_guest")
    private String total_guest;
    @SerializedName("purpose")
    private String purpose;
    @SerializedName("qr_code")
    private String qr_code;
    @SerializedName("host_cell")
    private String host_cell;
    @SerializedName("date")
    private String date;
    @SerializedName("time")
    private String time;

    public String getGuest_name() {
        return guest_name;
    }

    public String getGuest_cell() {
        return guest_cell;
    }

    public String getTotal_guest() {
        return total_guest;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getQr_code() {
        return qr_code;
    }

    public String getHost_cell() {
        return host_cell;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
