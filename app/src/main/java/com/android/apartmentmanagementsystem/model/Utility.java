package com.android.apartmentmanagementsystem.model;

import com.google.gson.annotations.SerializedName;

public class Utility {
    @SerializedName("cell")
    private String cell;
    @SerializedName("gas_bill_amount")
    private String gas_bill_amount;
    @SerializedName("electricity_bill_amount")
    private String electricity_bill_amount;
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

    public String getCell() {
        return cell;
    }

    public String getGas_bill_amount() {
        return gas_bill_amount;
    }

    public String getElectricity_bill_amount() {
        return electricity_bill_amount;
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
