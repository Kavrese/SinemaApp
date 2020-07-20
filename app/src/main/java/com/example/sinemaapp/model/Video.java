package com.example.sinemaapp.model;

import java.util.ArrayList;

public class Video {
    public String des_video;
    public String name_video;
    public String dislike_count;
    public String like_count;
    public String id_video;
    public String tag_video;
    public String channel_name;
    public String img;
    public String date_created;
    public String time_long;
    public Video(){}
    public Video(String channel_name,String des_video, String name_video, String tag_video, String like_count, String dislike_count, String id_video,String date_created,String img,String time_long){
        this.des_video = des_video;
        this.date_created = date_created;
        this.img = img;
        this.time_long = time_long;
        this.dislike_count = dislike_count;
        this.id_video = id_video;
        this.name_video = name_video;
        this.tag_video = tag_video;
        this.like_count = like_count;
        this.channel_name = channel_name;
    }


    public String getTime_long() {
        return time_long;
    }

    public String getDate_created() {
        return date_created;
    }

    public String getTag_video() {
        return tag_video;
    }

    public String getImg() {
        return img;
    }

    public String getId_video() {
        return id_video;
    }

    public String getChannel_name() {
        return channel_name;
    }

    public String getDes_video() {
        return des_video;
    }

    public String getDislike_count() {
        return dislike_count;
    }

    public String getLike_count() {
        return like_count;
    }

    public String getName_video() {
        return name_video;
    }
}
