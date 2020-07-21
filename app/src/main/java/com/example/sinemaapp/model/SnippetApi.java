package com.example.sinemaapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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

    @SerializedName("tags")
    @Expose
    private List<TagModel> tags;

    public SnippetApi(){}

    public SnippetApi (String publishedAt,String title,String description,ThumbnailApi thumbnails,List<TagModel> tags){
        this.description = description;
        this.thumbnails = thumbnails;
        this.title = title;
        this.publishedAt = publishedAt;
        this.tags = tags;
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

    public List<TagModel> getTags() {
        return tags;
    }
}
