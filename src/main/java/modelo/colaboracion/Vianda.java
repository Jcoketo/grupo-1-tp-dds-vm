package modelo.colaboracion;

import lombok.Getter;
import modelo.elementos.Heladera;
import lombok.Setter;
import modelo.personas.Colaborador;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "vianda")
public class Vianda{

    @Id
    @GeneratedValue
    @Getter private Long id;

    @Column
    private String tipoComida;

    @Column
    private LocalDate fechaCaducidad;

    @Column
    private LocalDate fechaDonacion;

    @ManyToOne
    @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
    private Colaborador colaborador;

    @ManyToOne
    @JoinColumn(name = "heladera_id", referencedColumnName = "id")
    private Heladera disponibleEn;

    @Column
    private Boolean entregada;

    @Column
    @Setter private Float calorias;

    @Column
    @Setter private Float peso;

    public Vianda(String tipoComida, LocalDate fechaCaducidad, LocalDate fechaDonacion, Colaborador colaborador, Heladera heladera, Boolean estado) {
        this.tipoComida = tipoComida;
        this.fechaCaducidad = fechaCaducidad;
        this.fechaDonacion = fechaDonacion;
        this.colaborador = colaborador;
        this.disponibleEn = heladera;
        this.entregada = estado;
    }

    public Vianda() {

    }
}