package com.krishigadmin.android.filepicker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddedImages {
    @SerializedName("gallery")
    @Expose
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}