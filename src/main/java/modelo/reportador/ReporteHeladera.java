package modelo.reportador;

import modelo.elementos.Heladera;

public class ReporteHeladera {
    private Heladera heladera;
    private Integer cantidad;

    // Constructor
    public ReporteHeladera() {}

    // Getter para heladera
    public Heladera getHeladera() {
        return heladera;
    }

    // Setter para heladera
    public void setHeladera(Heladera heladera) {
        this.heladera = heladera;
    }

    // Getter para cantidad
    public Integer getCantidad() {
        return cantidad;
    }

    // Setter para cantidad
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
