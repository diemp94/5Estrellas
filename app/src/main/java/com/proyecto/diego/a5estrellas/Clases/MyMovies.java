package com.proyecto.diego.a5estrellas.Clases;

public class MyMovies {

    private  String nombre;
    private  String info;
    private  String foto;

    public MyMovies(){}

    public MyMovies(String nombre, String info , String foto) {
        this.nombre = nombre;
        this.info = info;
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
