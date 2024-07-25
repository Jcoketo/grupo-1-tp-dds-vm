package repositorios;

import elementos.Incidente;

import java.util.List;

public class RepositorioIncidentes {
    public List<Incidente> incidentes;

    public void agregar(Incidente incidente){
        incidentes.add(incidente);
    }
}
