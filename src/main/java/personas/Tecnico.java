package personas;

import elementos.Heladera;
import elementos.Visita;
import repositorios.RepositorioVisitas;

public class Tecnico {
    private String nroCUIL;
    private String areaCobertura;
    private PersonaHumana persona;

    public Tecnico(String nroCUIL, String areaCobertura, PersonaHumana persona) {
        this.nroCUIL = nroCUIL;
        this.areaCobertura = areaCobertura;
        this.persona = persona;
    }

    public void registrarVisita(Heladera heladera, String descripcion, String URLfoto, Boolean incidenteSolucionado){
        //TODO
        Visita visita = new Visita(heladera, descripcion, URLfoto, incidenteSolucionado);
        RepositorioVisitas repo = RepositorioVisitas.getInstancia();
        repo.agregar(visita);

        if (incidenteSolucionado){
            heladera.marcarComoActiva();
        }

    }
}
