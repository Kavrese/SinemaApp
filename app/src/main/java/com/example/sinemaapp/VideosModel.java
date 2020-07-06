package com.example.sinemaapp;

public class VideosModel {
    private String name,des,date_created,img;
    public VideosModel (String name,String des,String date_created,String img){
        this.date_created = date_created;
        this.des = des;
        this.img = img;
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public String getDes() {
        return des;
    }

    public String getName() {
        return name;
    }

    public String getDate_created() {
        return date_created;
    }
}
