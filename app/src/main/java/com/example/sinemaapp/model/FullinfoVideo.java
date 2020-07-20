package com.example.sinemaapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FullinfoVideo {
    @SerializedName("items")
    @Expose
    private List<VideoApiFull> items;

    public FullinfoVideo(){}
    public FullinfoVideo(List<VideoApiFull> items){
        this.items = items;
    }

    public List<VideoApiFull> getItems() {
        return items;
    }
}
