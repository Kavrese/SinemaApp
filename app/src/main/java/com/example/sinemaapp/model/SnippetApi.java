package com.example.sinemaapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SnippetApi {
    @SerializedName("publishedAt")
    @Expose
    private String publishedAt;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("thumbnails")
    @Expose
    private ThumbnailApi thumbnails;

    public SnippetApi(){}

    public SnippetApi (String publishedAt,String title,String description,ThumbnailApi thumbnails){
        this.description = description;
        this.thumbnails = thumbnails;
        this.title = title;
        this.publishedAt = publishedAt;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public ThumbnailApi getThumbnails() {
        return thumbnails;
    }
}
