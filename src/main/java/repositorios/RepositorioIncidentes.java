package repositorios;

import elementos.FallaTecnica;
import elementos.Incidente;

import java.util.ArrayList;
import java.util.List;

public class RepositorioIncidentes {
    private static RepositorioIncidentes instancia = null;

    public static List<Incidente> incidentes;

    private RepositorioIncidentes() {
        incidentes = new ArrayList<Incidente>();
    }

    public static RepositorioIncidentes getInstancia() {
        if(instancia == null) {
            instancia = new RepositorioIncidentes();
        }
        return instancia;
    }
    public void agregar(Incidente incidente){
        incidentes.add(incidente);
        // notificar a los tecnicos
        // TODO API

    }


}
