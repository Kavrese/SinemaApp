package com.example.sinemaapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelMain {
    @SerializedName("nextPageToken")
    @Expose
    private String nextToken;
    @SerializedName("items")
    @Expose
    private List<VideoApi> items;
    public ModelMain(){}
    public ModelMain(String nextToken, List<VideoApi> items){
        this.items = items;
        this.nextToken = nextToken;
    }

    public String getNextToken() {
        return nextToken;
    }

    public List<VideoApi> getItems() {
        return items;
    }
}
