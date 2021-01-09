package com.android.apartmentmanagementsystem.model;

import com.google.gson.annotations.SerializedName;

public class Event {
    @SerializedName("event")
    private String event;
    @SerializedName("status")
    private String status;
    @SerializedName("date")
    private String date;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String message;
    public String getEvent() {
        return event;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}
