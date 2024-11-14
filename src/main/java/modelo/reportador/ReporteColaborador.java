package modelo.reportador;

import modelo.personas.Colaborador;

import javax.persistence.*;


public class ReporteColaborador {

    private Colaborador colaborador;

    private Integer cantidadViandas;


    // Constructor vacío
    public ReporteColaborador() {}

    // Getter para colaborador
    public Colaborador getColaborador() {
        return colaborador;
    }

    // Setter para colaborador
    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    // Getter para cantidadViandas
    public Integer getCantidadViandas() {
        return cantidadViandas;
    }

    // Setter para cantidadViandas
    public void setCantidadViandas(Integer cantidadViandas) {
        this.cantidadViandas = cantidadViandas;
    }


}
