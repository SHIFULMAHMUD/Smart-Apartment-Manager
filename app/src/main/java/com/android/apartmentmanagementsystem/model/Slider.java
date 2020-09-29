package com.android.apartmentmanagementsystem.model;

import com.google.gson.annotations.SerializedName;

public class Slider {
    @SerializedName("imageone")
    private String imageone;
    @SerializedName("imagetwo")
    private String imagetwo;
    @SerializedName("imagethree")
    private String imagethree;

    public Slider(String imageone, String imagetwo, String imagethree) {
        this.imageone = imageone;
        this.imagetwo = imagetwo;
        this.imagethree = imagethree;
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
}
