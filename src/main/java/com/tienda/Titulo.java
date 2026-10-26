package com.tienda;

public class Titulo {

    private int id;
    private String nombre;
    private String tipo;
    private int anio;
    private String genero;
    private String sinopsis;
    private String portada;
    private String estado;
    private int puntuacion;
    private String comentario;

    public Titulo(int id, String nombre, String tipo, int anio,
                  String genero, String sinopsis, String portada,
                  String estado, int puntuacion, String comentario) {

        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.anio = anio;
        this.genero = genero;
        this.sinopsis = sinopsis;
        this.portada = portada;
        this.estado = estado;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
    }
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public int getAnio() {
        return anio;
    }

    public String getGenero() {
        return genero;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public String getPortada() {
        return portada;
    }

    public String getEstado() {
        return estado;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public String getComentario() {
        return comentario;
    }
}

