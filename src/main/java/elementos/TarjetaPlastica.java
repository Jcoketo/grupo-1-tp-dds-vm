package elementos;

import personas.PersonaVulnerable;

import java.util.ArrayList;
import java.util.List;

public class TarjetaPlastica {
    private String id;
    private int usosDisponibles;
    private int usosRealizados;
    private PersonaVulnerable asociado;
    private List<UsoTarjetaPlastica> historialDeUsos;

    public TarjetaPlastica() {
        this.historialDeUsos = new ArrayList<UsoTarjetaPlastica>();
    }
}
