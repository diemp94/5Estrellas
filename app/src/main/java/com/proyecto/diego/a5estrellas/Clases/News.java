package com.proyecto.diego.a5estrellas.Clases;

public class News {

    private String nombre;
    private String imagen;
    private String url;

    public News(){}

    public News(String nombre, String imagen, String url) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.url = url;
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getImagen() { return imagen; }

    public void setImagen(String imagen) { this.imagen = imagen; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }
}
