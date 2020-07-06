package com.example.sinemaapp;

public class Film {
    private String name;
    private String date_create;
    private String des;
    private String img;
    private String time_long;
    private String tag;
    private float star;
    public Film (String name,String date_create,String des,String img,String time_long,String tag,float star){
        this.name = name;
        this.date_create = date_create;
        this.des = des;
        this.img = img;
        this.time_long = time_long;
        this.tag = tag;
        this.star = star;
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
