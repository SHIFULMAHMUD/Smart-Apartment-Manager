package com.android.apartmentmanagementsystem.model;

import com.google.gson.annotations.SerializedName;

public class Task {
    @SerializedName("cell")
    private String cell;
    @SerializedName("guard_name")
    private String guard_name;
    @SerializedName("task")
    private String task;
    @SerializedName("date")
    private String date;
    @SerializedName("time")
    private String time;

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
}
