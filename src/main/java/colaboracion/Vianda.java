package colaboracion;

import elementos.Heladera;
import personas.Colaborador;

import java.time.LocalDateTime;
import java.util.List;

public class Vianda{
    private Float peso;
    private Float calorias;
    private List<String> tipoComida;
    private LocalDateTime fechaDonacion;
    private LocalDateTime fechaCaducidad;
    private Boolean entregado;
    private Colaborador colaborador;
    private Heladera disponibleEn;

    public Vianda(Float peso, Float calorias, List<String> tipoComida, LocalDateTime fechaCaducidad, Colaborador colaborador, Heladera heladera) {
        this.peso = peso;
        this.calorias = calorias;
        this.tipoComida = tipoComida;
        this.fechaDonacion = LocalDateTime.now();
        this.fechaCaducidad = fechaCaducidad;
        this.entregado = false;
        this.colaborador = colaborador;
        this.disponibleEn = heladera;
    }
}
