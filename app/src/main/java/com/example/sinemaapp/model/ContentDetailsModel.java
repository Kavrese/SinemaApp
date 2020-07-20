package com.example.sinemaapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContentDetailsModel {
    @SerializedName("duration")
    @Expose
    String duration;
    public ContentDetailsModel(){}
    public ContentDetailsModel(String duration){
        this.duration = duration;
    }

    public String getDuration() {
        return duration;
    }
}
