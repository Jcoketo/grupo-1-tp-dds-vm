package org.example;

import java.rmi.MarshalledObject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.List;


public class Envio {

  private Persona destinatario;
  private Persona remitente;
  private Double precio;
  private Integer codigoRastreo;
  private Empleado cartero;
  private List<Movimiento> camino;
  private Boolean entregado;
  private pos

  public void registrarMovimiento(Sucursal sucursal, Empleado cartero){
    
      LocalDateTime fecha = LocalDateTime.now();
      Movimiento movimiento = new Movimiento(sucursal, fecha);
      
      this.camino.add(movimiento);

      if (sucursal.getLocalidad() == destinatario.getLocalidad()){
        this.cartero = cartero;
      }
  }

  public Movimiento ultimoMovimiento(){
    Integer ultimo = this.camino.size() - 1;

    return this.camino.get(ultimo);
  }

  public Sucursal buscarUbicacionActual(){

    return ultimoMovimiento().getSucursal();

  }

  public Envio(Persona remitente, Persona destinatario, Double precio, Integer codigoRastreo) {
    this.destinatario = destinatario;
    this.remitente = remitente;
    this.precio = precio;
    this.codigoRastreo = codigoRastreo;
    this.camino = new ArrayList<>();
    this.entregado = Boolean.FALSE;
  }

}
