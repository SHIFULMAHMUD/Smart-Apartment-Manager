package com.android.apartmentmanagementsystem.model;

import com.google.gson.annotations.SerializedName;

public class Report {
    @SerializedName("cell")
    private String cell;
    @SerializedName("rent_of_month")
    private String rent_of_month;
    @SerializedName("rent_amount")
    private String rent_amount;
    @SerializedName("paying_date")
    private String paying_date;
    @SerializedName("gas_bill_amount")
    private String gas_bill_amount;
    @SerializedName("electricity_bill_amount")
    private String electricity_bill_amount;

    public String getCell() {
        return cell;
    }

    public String getRent_of_month() {
        return rent_of_month;
    }

    public String getRent_amount() {
        return rent_amount;
    }

    public String getPaying_date() {
        return paying_date;
    }

    public String getGas_bill_amount() {
        return gas_bill_amount;
    }

    public String getElectricity_bill_amount() {
        return electricity_bill_amount;
    }
}
