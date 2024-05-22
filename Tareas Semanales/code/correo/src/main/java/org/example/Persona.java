package org.example;

public class Persona {
    private String nombre;
    private String apellido;
    private Integer codigoPostal;
    private String localidad;
    private TipoPersona tipo;

    public String getLocalidad() {
        return localidad;
    }

    public String getNombre() {
        return nombre;
    }

    public Persona(String nombre, String localidad) {
        this.nombre = nombre;
        this.localidad = localidad;
    }



}

