package com.android.apartmentmanagementsystem.model;

import com.google.gson.annotations.SerializedName;

public class Contacts {


    @SerializedName("name")
    private String name;
    @SerializedName("cell")
    private String cell;
    @SerializedName("password")
    private String password;
    @SerializedName("account")
    private String account;
    @SerializedName("gender")
    private String gender;
    @SerializedName("nid")
    private String nid;
    @SerializedName("nid_pic")
    private String nid_pic;

    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String massage;

    @SerializedName("date")
    private String date;
    @SerializedName("notice")
    private String notice;
    @SerializedName("event")
    private String event;

    @SerializedName("time")
    private String time;



    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public String getAccount() {
        return account;
    }

    public String getCell() {
        return cell;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getNid() {
        return nid;
    }

    public String getNidPhoto() {
        return nid_pic;
    }

    public String getValue() {
        return value;
    }

    public String getMassage() {
        return massage;
    }

    public String getDate() { return date; }

    public String getNotice() {
        return notice;
    }

    public String getEvent() { return event; }


}