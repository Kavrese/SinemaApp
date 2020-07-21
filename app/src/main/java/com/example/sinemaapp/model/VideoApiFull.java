package com.example.sinemaapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoApiFull {
    @SerializedName("statistics")
    @Expose
    StatisticsVideo statisticsVideo;

    @SerializedName("contentDetails")
    @Expose
    ContentDetailsModel contentDetails;

    @SerializedName("snippet")
    @Expose
    SnippetApiFull snippetApi;


    public VideoApiFull (){}
    public VideoApiFull (StatisticsVideo statisticsVideo,ContentDetailsModel contentDetails){
        this.contentDetails = contentDetails;
        this.statisticsVideo = statisticsVideo;
    }

    public ContentDetailsModel getContentDetails() {
        return contentDetails;
    }

    public StatisticsVideo getStatisticsVideo() {
        return statisticsVideo;
    }

    public SnippetApiFull getSnippetApi() {
        return snippetApi;
    }
}
