package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Movimiento {
  private Sucursal sucursal;
  private LocalDateTime fecha;


public Movimiento(Sucursal sucursal, LocalDateTime fecha){
  this.sucursal = sucursal;
  this.fecha = fecha;
}

  public Sucursal getSucursal(){
  return sucursal;
}

}


