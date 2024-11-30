package modelo.personas;

import lombok.Getter;
import modelo.colaboracion.Vianda;
import modelo.elementos.Heladera;
import modelo.personas.Tecnico;

import javax.persistence.*;
import javax.ws.rs.CookieParam;

@Entity
@Table(name = "visita")
public class Visita {

    @Id
    @GeneratedValue
    @Getter private Long id;

    @ManyToOne
    @JoinColumn(name ="heladera_id", referencedColumnName = "id")
    @Getter
    private Heladera heladera;

    @ManyToOne
    @JoinColumn(name ="tecnico_id", referencedColumnName = "id")
    @Getter
    private Tecnico tecnico;

    @Column
    @Getter
    private String descripcion;
    @Column
    private String URLfoto;
    @Column
    private Boolean incidenteSolucionado;

    public Visita(Heladera heladera, String descripcion, String URLfoto, Boolean incidenteSolucionado) {
        this.heladera = heladera;
        this.descripcion = descripcion;
        this.URLfoto = URLfoto;
        this.incidenteSolucionado = incidenteSolucionado;
    }

    public Visita() {

    }
}
