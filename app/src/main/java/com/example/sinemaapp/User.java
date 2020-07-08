package com.example.sinemaapp;

public class User {
    public String avatar_icon;
    public String email;
    public String password;
    public boolean premium;
    public int user_id;
    public String user_name;
    public User (){}
    public User(String avatar_icon,String email,String password,boolean premium,int user_id,String user_name){
        this.avatar_icon = avatar_icon;
        this.email = email;
        this.password = password;
        this.premium = premium;
        this.user_id = user_id;
        this.user_name = user_name;
    }

    public int getUser_id() {
        return user_id;
    }
}
