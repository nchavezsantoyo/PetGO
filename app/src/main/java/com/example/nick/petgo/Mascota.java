package com.example.nick.petgo;

public class Mascota {
    String id_mascota;
    String id_usuario;
    String categoria;
    String especie;
    String sexo;
    String tamano;
    String descripcion;
    String fecha;
    String foto;

    public Mascota() { }

    public Mascota(String id_mascota, String id_usuario, String categoria, String especie, String sexo, String tamano, String descripcion, String fecha, String foto) {
        this.id_mascota = id_mascota;
        this.id_usuario = id_usuario;
        this.categoria = categoria;
        this.especie = especie;
        this.sexo = sexo;
        this.tamano = tamano;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.foto = foto;
    }

    public String getId_mascota() {
        return id_mascota;
    }

    public void setId_mascota(String id_mascota) {
        this.id_mascota = id_mascota;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
