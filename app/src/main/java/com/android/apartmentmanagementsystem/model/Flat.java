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
    @SerializedName("gas_bill")
    private String gas_bill;
    @SerializedName("electricity_bill")
    private String electricity_bill;
    @SerializedName("imageone")
    private String imageone;
    @SerializedName("imagetwo")
    private String imagetwo;
    @SerializedName("imagethree")
    private String imagethree;
    @SerializedName("request")
    private String request;
    @SerializedName("renter_name")
    private String renter_name;
    @SerializedName("renter_cell")
    private String renter_cell;
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

    public String getRenter_name() {
        return renter_name;
    }

    public String getRenter_cell() {
        return renter_cell;
    }

    public String getGas_bill() {
        return gas_bill;
    }

    public String getElectricity_bill() {
        return electricity_bill;
    }
}
