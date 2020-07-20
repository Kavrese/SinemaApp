package com.example.sinemaapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ThumbnailApi {
    @SerializedName("high")
    @Expose
    private HighThumb high;

    public ThumbnailApi (){}

    public ThumbnailApi(HighThumb high){
        this.high = high;
    }

    public HighThumb getHigh() {
        return high;
    }
    public class HighThumb {
        @SerializedName("url")
        @Expose
        private String url;

        public HighThumb(){}

        public HighThumb(String url){
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }
}
