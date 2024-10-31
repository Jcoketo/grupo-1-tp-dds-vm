package modelo.elementos;

import lombok.Getter;

import javax.persistence.*;
import javax.ws.rs.CookieParam;

@Entity
@Table(name = "visita")
public class Visita {

    @Id
    @GeneratedValue
    @Getter private Long id;

    @ManyToOne
    Heladera heladera;

    @Column
    String descripcion;
    @Column
    String URLfoto;
    @Column
    Boolean incidenteSolucionado;

    public Visita(Heladera heladera, String descripcion, String URLfoto, Boolean incidenteSolucionado) {
        this.heladera = heladera;
        this.descripcion = descripcion;
        this.URLfoto = URLfoto;
        this.incidenteSolucionado = incidenteSolucionado;
    }
}
