package org.example;

import java.util.ArrayList;
import java.util.List;

public class Sucursal{
  private int numero;
  private String localidad;
  private String domicilio;
  private List<Empleado> empleados;
  private List<Envio> envios;

  public int getNumero() {
    return numero;
  }

  public String getLocalidad() {
    return localidad;
  }

  public Sucursal(Integer numero, String domicilio) {
    this.numero = numero;
    this.empleados = new ArrayList<>();
    this.envios = new ArrayList<>();
  }
}
