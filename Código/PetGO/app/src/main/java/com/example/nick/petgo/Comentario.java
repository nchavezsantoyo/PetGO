package com.example.nick.petgo;

public class Comentario {
    private String id_comentario;
    private String id_mascota;
    private String contenido;
    private String id_usario;
    private String fecha;

    public Comentario (){}

    public Comentario(String id_comentario, String id_mascota, String contenido, String id_usario, String fecha) {
        this.id_comentario = id_comentario;
        this.id_mascota = id_mascota;
        this.contenido = contenido;
        this.id_usario = id_usario;
        this.fecha = fecha;
    }

    public String getId_comentario() {
        return id_comentario;
    }

    public void setId_comentario(String id_comentario) {
        this.id_comentario = id_comentario;
    }

    public String getId_mascota() {
        return id_mascota;
    }

    public void setId_mascota(String id_mascota) {
        this.id_mascota = id_mascota;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getId_usario() {
        return id_usario;
    }

    public void setId_usario(String id_usario) {
        this.id_usario = id_usario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
