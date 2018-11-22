package com.example.nick.petgo;

public class Mensaje {
    String id_mensaje;
    String id_usuario;
    String mensaje;
    String destino;

    public Mensaje(){}

    public Mensaje(String id_mensaje, String id_usuario, String mensaje, String destino) {
        this.id_mensaje = id_mensaje;
        this.id_usuario = id_usuario;
        this.mensaje = mensaje;
        this.destino = destino;
    }

    public String getId_mensaje() {
        return id_mensaje;
    }

    public void setId_mensaje(String id_mensaje) {
        this.id_mensaje = id_mensaje;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
}
