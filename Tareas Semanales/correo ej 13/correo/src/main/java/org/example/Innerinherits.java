package org.example;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;


public class Envio {

  private Persona destinatario;
  private Persona remitente;
  private Double precio;
  private Integer codigoRastreo;
  private TipoEmpleado cartero;
  private ArrayList<Movimiento> camino;
  private Boolean entregado;

  public void registrarMovimiento(Sucursal sucursal){
    
      LocalDate fecha = LocalDate.now();
      Movimiento movimiento = new Movimiento(sucursal, fecha);
      
      camino.add(movimiento);
  }

  public Sucursal buscarUbicacionActual(){
  
    int ultimo = camino.size() - 1;

    Movimiento ultimoCamino = camino.get(ultimo);

    return ultimoCamino.getSucursal();

  }
}

public class Telegrama extends Envio{

  
}
