package com.example.sinemaapp;

public class StaticsVideos {
    private String see,like,dis;
    public StaticsVideos (String see,String like,String dis){
        this.dis = dis;
        this.like = like;
        this.see = see;
    }

    public String getDis() {
        return dis;
    }

    public String getLike() {
        return like;
    }

    public String getSee() {
        return see;
    }
}
