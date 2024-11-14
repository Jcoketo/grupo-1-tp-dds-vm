package modelo.personas;

import java.util.ArrayList;
import lombok.Getter;
import modelo.elementos.Areas;
import modelo.elementos.Heladera;
import modelo.elementos.Incidente;
import modelo.elementos.Visita;

import javax.persistence.*;
import java.util.List;


@Entity
@Table
public class Tecnico {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String nroCUIL;

    @Enumerated
    @Getter private Areas areaCobertura;

    @OneToOne
    @JoinColumn(name = "persona_id", referencedColumnName = "id")
    private PersonaHumana persona;

    @OneToMany
    private List<Visita> visitas = new ArrayList<>();


    public Tecnico(String nroCUIL, Areas areaCobertura, PersonaHumana persona) {
        this.nroCUIL = nroCUIL;
        this.areaCobertura = areaCobertura;
        this.persona = persona;
    }

    public Tecnico() {

    }

    public void registrarVisita(Heladera heladera, String descripcion, String URLfoto, Boolean incidenteSolucionado){
        Visita visita = new Visita(heladera, descripcion, URLfoto, incidenteSolucionado);

        /* ALTERNATIVA CON REPO:
        RepositorioVisitas repo = RepositorioVisitas.getInstancia();
        repo.agregar(visita); */

        this.visitas.add(visita);
        heladera.agregarVisita(visita);

        if (incidenteSolucionado){
            heladera.marcarComoActiva();
        }

    }

    public void notificarFalla(Heladera heladera, Incidente incidente) {
        //TODO
        // enviar notificacion al tecnico
        // enviar notificacion al area de mantenimiento
    }

}
