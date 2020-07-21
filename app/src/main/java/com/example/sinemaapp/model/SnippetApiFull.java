package com.example.sinemaapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SnippetApiFull {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("tags")
    @Expose
    private List<String> tags;

    public SnippetApiFull(){}

    public SnippetApiFull (String title,String description,List<String> tags){
        this.description = description;
        this.title = title;
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }


    public List<String> getTags() {
        return tags;
    }
}
