package com.example.sinemaapp.model;

public class User {
    public String avatar_icon;
    public String email;
    public String password;
    public boolean premium;
    public String user_name;
    public User (){}
    public User(String avatar_icon,String email,String password,boolean premium,String user_name){
        this.avatar_icon = avatar_icon;
        this.email = email;
        this.password = password;
        this.premium = premium;
        this.user_name = user_name;
    }
}
