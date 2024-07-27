package elementos;

import personas.PersonaVulnerable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TarjetaPlastica extends Tarjeta {
    private int usosDisponibles;
    private int usosConsumidos;
    private PersonaVulnerable asociado;
    private List<UsoTarjetaPlastica> historialDeUsos;

    public TarjetaPlastica() {
        this.historialDeUsos = new ArrayList<UsoTarjetaPlastica>();
    }

    @Override
    public void registrarUso(Heladera heladera) {
        this.historialDeUsos.add(new UsoTarjetaPlastica(heladera, LocalDateTime.now()));
        this.usosConsumidos += 1;
    }
}