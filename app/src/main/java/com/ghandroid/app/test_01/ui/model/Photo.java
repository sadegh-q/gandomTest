package com.ghandroid.app.test_01.ui.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Photo {

    @SerializedName("description")
    private String description;

    @SerializedName("images")
    private List<String> images;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImageUrls() {
        return images;
    }

    public void setImageUrls(List<String> images) {
        this.images = images;
    }
}
