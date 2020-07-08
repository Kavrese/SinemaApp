package com.example.sinemaapp;
public class Video {
    public String des_video;
    public String name_video;
    public String dislike_count;
    public String like_count;
    public String id_video;
    public String tag_video;
    public String channel_name;
    public Video(){}
    public Video(String channel_name,String des_video, String name_video, String tag_video, String like_count, String dislike_count, String id_video){
        this.des_video = des_video;
        this.dislike_count = dislike_count;
        this.id_video = id_video;
        this.name_video = name_video;
        this.tag_video = tag_video;
        this.like_count = like_count;
        this.channel_name = channel_name;
    }

    public String getId_video() {
        return id_video;
    }

    public String getChannel_name() {
        return channel_name;
    }
}
