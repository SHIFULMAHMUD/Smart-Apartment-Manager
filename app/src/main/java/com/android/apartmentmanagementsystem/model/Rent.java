package com.android.apartmentmanagementsystem.model;

import com.google.gson.annotations.SerializedName;

public class Rent {
    @SerializedName("getcell")
    private String getcell;
    @SerializedName("rent_amount")
    private String rent_amount;
    @SerializedName("rent_of_month")
    private String rent_of_month;
    @SerializedName("bkash_trx_id")
    private String bkash_trx_id;
    @SerializedName("bkash_cell")
    private String bkash_cell;
    @SerializedName("paying_date")
    private String paying_date;
    @SerializedName("paying_time")
    private String paying_time;

    public String getGetcell() {
        return getcell;
    }

    public String getRent_amount() {
        return rent_amount;
    }

    public String getRent_of_month() {
        return rent_of_month;
    }

    public String getBkash_trx_id() {
        return bkash_trx_id;
    }

    public String getBkash_cell() {
        return bkash_cell;
    }

    public String getPaying_date() {
        return paying_date;
    }

    public String getPaying_time() {
        return paying_time;
    }
}
