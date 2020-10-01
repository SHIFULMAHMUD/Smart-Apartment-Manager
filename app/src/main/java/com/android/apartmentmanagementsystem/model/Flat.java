package com.android.apartmentmanagementsystem.model;

import com.google.gson.annotations.SerializedName;

public class Flat {
    @SerializedName("flat_no")
    private String flat_no;
    @SerializedName("floor_no")
    private String floor_no;
    @SerializedName("flat_details")
    private String flat_details;
    @SerializedName("flat_price")
    private String flat_price;
    @SerializedName("imageone")
    private String imageone;
    @SerializedName("imagetwo")
    private String imagetwo;
    @SerializedName("imagethree")
    private String imagethree;
    @SerializedName("request")
    private String request;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String massage;

    public String getFlat_no() {
        return flat_no;
    }

    public String getFloor_no() {
        return floor_no;
    }

    public String getFlat_details() {
        return flat_details;
    }

    public String getFlat_price() {
        return flat_price;
    }

    public String getImageone() {
        return imageone;
    }

    public String getImagetwo() {
        return imagetwo;
    }

    public String getImagethree() {
        return imagethree;
    }

    public String getRequest() {
        return request;
    }

    public String getValue() {
        return value;
    }

    public String getMassage() {
        return massage;
    }
}
