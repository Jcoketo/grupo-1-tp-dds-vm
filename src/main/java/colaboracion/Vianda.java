package colaboracion;

import elementos.Heladera;
import personas.Colaborador;

import java.time.LocalDateTime;
import java.util.List;

public class Vianda{
    private List<String> tipoComida;
    private LocalDateTime fechaCaducidad;
    private LocalDateTime fechaDonacion;
    private Colaborador colaborador;
    private Heladera disponibleEn;
    private Float calorias;
    private Float peso;
    private Boolean entregada;

    public Vianda(Float peso, Float calorias, List<String> tipoComida, LocalDateTime fechaCaducidad, Colaborador colaborador, Heladera heladera) {
        this.peso = peso;
        this.calorias = calorias;
        this.tipoComida = tipoComida;
        this.fechaDonacion = LocalDateTime.now();
        this.fechaCaducidad = fechaCaducidad;
        this.entregada = false;
        this.colaborador = colaborador;
        this.disponibleEn = heladera;
    }
}