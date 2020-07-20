package com.example.sinemaapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatisticsVideo {
    @SerializedName("viewCount")
    @Expose
    String viewCount;

    @SerializedName("likeCount")
    @Expose
    String likeCount;

    @SerializedName("dislikeCount")
    @Expose
    String dislikeCount;

    public StatisticsVideo(){}
    public StatisticsVideo(String viewCount,String likeCount,String dislikeCount){
        this.dislikeCount = dislikeCount;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
    }

    public String getDislikeCount() {
        return dislikeCount;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public String getViewCount() {
        return viewCount;
    }
}
