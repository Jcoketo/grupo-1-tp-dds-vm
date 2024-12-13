package modelo.personas;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
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
    @Getter @Setter private String nroCUIL;

    @Enumerated
    @Getter private Areas areaCobertura;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "persona_id", referencedColumnName = "id")
    @Getter private PersonaHumana persona;

    @OneToMany(mappedBy = "tecnico", cascade = CascadeType.PERSIST)
    private List<Visita> visitas = new ArrayList<>();

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "punto_estrategico_id", referencedColumnName = "id")
    @Getter private PuntoEstrategico puntoEstrategico;


    public Tecnico(String nroCUIL, Areas areaCobertura, PersonaHumana persona) {
        this.nroCUIL = nroCUIL;
        this.areaCobertura = areaCobertura;
        this.persona = persona;
    }

    public Tecnico() {

    }

    public Tecnico (PersonaHumana persona, String cuil, PuntoEstrategico puntoEstrategico) {
        this.nroCUIL = cuil;
        this.areaCobertura = puntoEstrategico.getAreas();
        this.persona = persona;
        this.puntoEstrategico = puntoEstrategico;
    }

    public void registrarVisita(Heladera heladera, Visita visita){
        this.visitas.add(visita);
        heladera.agregarVisita(visita);
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
