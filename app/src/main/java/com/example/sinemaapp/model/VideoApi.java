package com.example.sinemaapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoApi {
    @SerializedName("id")
    @Expose
    private VideoID id;
    @SerializedName("snippet")
    @Expose
    private SnippetApi snippet;

    public VideoApi(){}
    public VideoApi(VideoID id,SnippetApi snippet){
        this.snippet = snippet;
        this.id = id;
    }

    public SnippetApi getSnippet() {
        return snippet;
    }

    public VideoID getId() {
        return id;
    }
}
