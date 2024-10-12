package persistencia;

import modelo.personas.PersonaVulnerable;
import modelo.reportador.Reporte;

import java.util.ArrayList;
import java.util.List;

public class RepositorioPersonasVulnerables {

    private List<PersonaVulnerable> personasVulnerables;

    private static RepositorioPersonasVulnerables instancia = null;


    public RepositorioPersonasVulnerables() {
        this.personasVulnerables = new ArrayList<>();
    }

    public static RepositorioPersonasVulnerables getInstancia() {
        if(instancia == null) {
            instancia = new RepositorioPersonasVulnerables();
        }
        return instancia;
    }

    public void agregarPersonaVulnerable(PersonaVulnerable PV){personasVulnerables.add(PV);}

}
