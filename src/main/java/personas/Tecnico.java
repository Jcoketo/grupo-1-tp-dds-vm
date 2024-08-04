package personas;

import elementos.*;
import java.util.ArrayList;
import lombok.Getter;
import repositorios.RepositorioVisitas;
import java.util.List;

public class Tecnico {
    private String nroCUIL;
    @Getter private Areas areaCobertura;
    private PersonaHumana persona;
    private List<Visita> visitas = new ArrayList<>();

    public Tecnico(String nroCUIL, Areas areaCobertura, PersonaHumana persona) {
        this.nroCUIL = nroCUIL;
        this.areaCobertura = areaCobertura;
        this.persona = persona;
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
