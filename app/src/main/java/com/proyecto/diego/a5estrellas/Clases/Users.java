package com.proyecto.diego.a5estrellas.Clases;

import java.util.ArrayList;

public class Users {

    String username;
    String email;
    String uid;
    MyMovies mv;

    public Users (){}

    public Users(String username,String email,String uid, MyMovies mv){
        this.username = username;
        this.email = email;
        this.uid = uid;
        this.mv = mv;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public MyMovies getMv() {
        return mv;
    }

    public void setMv(MyMovies mv) {
        this.mv = mv;
    }
}
