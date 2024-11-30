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
    @Getter
    private String tipoComida;

    @Column
    @Getter
    private LocalDate fechaCaducidad;

    @Column
    @Getter
    private LocalDate fechaDonacion;

    @ManyToOne
    @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
    @Getter
    private Colaborador colaborador;

    @ManyToOne
    @JoinColumn(name = "heladera_id", referencedColumnName = "id")
    @Getter
    private Heladera disponibleEn;

    @Column
    @Getter @Setter
    private Boolean entregada;

    @Column
    @Getter
    @Setter private Float calorias;

    @Column
    @Getter
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