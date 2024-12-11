package modelo.personas;

import java.util.ArrayList;
import lombok.Getter;
import modelo.elementos.Areas;
import modelo.elementos.Heladera;
import modelo.elementos.PuntoEstrategico;
import modelo.elementos.TipoAlerta;
import modelo.notificador.Notificador;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Tecnico {
    @Id
    @GeneratedValue
    @Getter private int id;

    @Column
    @Getter private String nroCUIL;

    @Enumerated
    @Getter private Areas areaCobertura;

    @OneToOne
    @JoinColumn(name = "persona_id", referencedColumnName = "id")
    @Getter private PersonaHumana persona;

    @OneToMany(mappedBy = "tecnico")
    private List<Visita> visitas = new ArrayList<>();

    @Transient
    @Getter private PuntoEstrategico puntoEstrategico;
    // TODO FALTA DEFINIR QUE HACEMOS CON ESTE PUNTO ESTRATEGICO
    // LO NECESITAMOS PARA VER CUAL ES EL TECNICO MAS CERCANO

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

    public void notificarFalla(Heladera heladera, String descripcion){
        String texto = "Se ha reportado una falla en la heladera " + heladera.getNombre() + " en " + heladera.getPuntoEstrategico().getDireccion() + ". " +
                "Descripción del problema: " + descripcion;
        String asunto = "Falla técnica reportada";

        MedioDeContacto medio = persona.devolerMedioDeContacto(TipoMedioDeContacto.MAIL);
        Notificador.notificar(texto, asunto, medio);
    }

    public void notificarAlerta(Heladera heladera, TipoAlerta alerta){
        String texto = "Se ha reportado una alerta en la heladera " + heladera.getNombre() + " en " + heladera.getPuntoEstrategico().getDireccion() + ". " +
                "Tipo de alerta: " + alerta;
        String asunto = "Alerta reportada";

        MedioDeContacto medio = persona.devolerMedioDeContacto(TipoMedioDeContacto.MAIL);
        Notificador.notificar(texto, asunto, medio);
    }

}
