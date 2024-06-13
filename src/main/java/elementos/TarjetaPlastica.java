package elementos;

import personas.PersonaVulnerable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TarjetaPlastica {
    private String id;
    private int usosDisponibles;
    private int usosConsumidos;
    private PersonaVulnerable asociado;
    private List<UsoTarjetaPlastica> historialDeUsos;

    public TarjetaPlastica() {
        this.historialDeUsos = new ArrayList<UsoTarjetaPlastica>();
    }

    public void registrarUso(Heladera heladera, LocalDateTime fechaYhora) {
        this.historialDeUsos.add(new UsoTarjetaPlastica(heladera, fechaYhora));
        this.usosConsumidos += 1;
    }
}
