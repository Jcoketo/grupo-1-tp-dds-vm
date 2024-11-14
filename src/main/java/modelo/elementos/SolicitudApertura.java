package modelo.elementos;

import lombok.Getter;
import modelo.personas.Colaborador;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
public class SolicitudApertura {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "heladera_id", referencedColumnName = "id")
    private Heladera heladera;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
    private Colaborador solicitanteApertura;

    @Column
    @Getter private LocalDateTime fechaSolicitud;
    @Column
    @Getter private LocalDateTime aperturaFehaciente;
    @Transient
    @Getter private static Integer horasDeApertura = 3;


    public SolicitudApertura(Heladera heladera, Colaborador solicitanteApertura){
        this.heladera = heladera;
        this.solicitanteApertura = solicitanteApertura;
        this.fechaSolicitud = LocalDateTime.now();
    }

    public SolicitudApertura() {

    }


    public void setAperturaFehaciente(){
        this.aperturaFehaciente = LocalDateTime.now();
    }



}