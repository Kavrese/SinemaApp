package com.example.sinemaapp.model;

public class User {
    public String avatar_icon;
    public String email;
    public String password;
    public boolean premium;
    public String user_name;
    public String date_created;
    public User (){}
    public User(String avatar_icon,String email,String password,boolean premium,String user_name,String date_created){
        this.avatar_icon = avatar_icon;
        this.email = email;
        this.password = password;
        this.premium = premium;
        this.date_created = date_created;
        this.user_name = user_name;
    }
}
