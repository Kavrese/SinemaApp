package com.example.sinemaapp.model;

public class Film {
    private String name;
    private String date_create;
    private String des;
    private String img;
    private String time_long;
    private String tag;
    private String id_video;
    private float star;
    public Film (String name,String date_create,String des,String img,String time_long,String tag,float star,String id_video){
        this.name = name;
        this.date_create = date_create;
        this.des = des;
        this.img = img;
        this.time_long = time_long;
        this.tag = tag;
        this.star = star;
        this.id_video = id_video;
    }
    public float getStar() {
        return star;
    }
    public String getTime_long() {
        return time_long;
    }
    public String getTag() {
        if(tag != null)
            return tag;
        else
            return "";
    }

    public String getId_video() {
        return id_video;
    }

    public String getDate_create() {
        return date_create;
    }

    public String getDes() {
        return des;
    }

    public String getImg() {
        return img;
    }

    public String getName() {
        return name;
    }
}
