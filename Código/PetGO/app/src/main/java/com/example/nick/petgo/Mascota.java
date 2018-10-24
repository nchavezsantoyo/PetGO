package com.example.nick.petgo;

public class Mascota {
    String id_mascota;
    String id_usuario;
    String categoria;
    String especie;
    String sexo;
    String tamano;
    String edad;
    String descripcion;

    public Mascota(String id_mascota, String id_usuario, String categoria, String especie, String sexo, String tamano, String edad, String descripcion) {
        this.id_mascota = id_mascota;
        this.id_usuario = id_usuario;
        this.categoria = categoria;
        this.especie = especie;
        this.sexo = sexo;
        this.tamano = tamano;
        this.edad = edad;
        this.descripcion = descripcion;
    }
}
