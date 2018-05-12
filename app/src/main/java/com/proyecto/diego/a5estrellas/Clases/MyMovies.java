package com.proyecto.diego.a5estrellas.Clases;

import java.io.Serializable;

public class MyMovies implements Serializable {

    private  String nombre;
    private  String info;
    private  String foto;
    private  String infoDetalle;
    private String trailer;

    public MyMovies(){}

    public MyMovies(String nombre, String info , String foto, String infoDetalle, String trialer) {
        this.nombre = nombre;
        this.info = info;
        this.foto = foto;
        this.infoDetalle = infoDetalle;
        this.trailer = trailer;
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

    public String getInfoDetalle() {
        return infoDetalle;
    }

    public void setInfoDetalle(String infoDetalle) {
        this.infoDetalle = infoDetalle;
    }

    public String getTrailer() { return trailer; }

    public void setTrailer(String trailer) { this.trailer = trailer; }
}
