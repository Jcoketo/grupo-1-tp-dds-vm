package modelo.colaboracion;

import modelo.elementos.Heladera;
import lombok.Setter;
import modelo.personas.Colaborador;

import java.time.LocalDate;

public class Vianda{
    private String tipoComida;
    private LocalDate fechaCaducidad;
    private LocalDate fechaDonacion;
    private Colaborador colaborador;
    private Heladera disponibleEn;
    private Boolean entregada;
    @Setter private Float calorias;
    @Setter private Float peso;

    public Vianda(String tipoComida, LocalDate fechaCaducidad, LocalDate fechaDonacion, Colaborador colaborador, Heladera heladera, Boolean estado) {
        this.tipoComida = tipoComida;
        this.fechaCaducidad = fechaCaducidad;
        this.fechaDonacion = fechaDonacion;
        this.colaborador = colaborador;
        this.disponibleEn = heladera;
        this.entregada = estado;
    }
}