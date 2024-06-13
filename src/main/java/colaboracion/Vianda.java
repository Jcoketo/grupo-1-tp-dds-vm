package colaboracion;

import elementos.Heladera;
import lombok.Setter;
import personas.Colaborador;

import java.time.LocalDateTime;
import java.util.List;

public class Vianda{
    private String tipoComida;
    private LocalDateTime fechaCaducidad;
    private LocalDateTime fechaDonacion;
    private Colaborador colaborador;
    private Heladera disponibleEn;
    private Boolean entregada;
    @Setter private Float calorias;
    @Setter private Float peso;

    public Vianda(String tipoComida, LocalDateTime fechaCaducidad, LocalDateTime fechaDonacion, Colaborador colaborador, Heladera heladera, Boolean estado) {
        this.tipoComida = tipoComida;
        this.fechaCaducidad = fechaCaducidad;
        this.fechaDonacion = fechaDonacion;
        this.colaborador = colaborador;
        this.disponibleEn = heladera;
        this.entregada = estado;
    }
}