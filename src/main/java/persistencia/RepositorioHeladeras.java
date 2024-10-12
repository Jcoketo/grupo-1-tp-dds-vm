package persistencia;

import modelo.elementos.Heladera;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class RepositorioHeladeras {
    private static RepositorioHeladeras instancia = null;
    @Getter
    private static List<Heladera> heladeras;

    private RepositorioHeladeras() {
        heladeras = new ArrayList<>();
    }

    public static RepositorioHeladeras getInstancia() {
        if(instancia == null) {
            instancia = new RepositorioHeladeras();
        }
        return instancia;
    }

    public void agregarHeladera(Heladera heladera) {
        this.heladeras.add(heladera);
    }

    public List<Heladera> obtenerHeladerasCercanas(Heladera heladeraAfectada, Integer cantidadHeladerasCercanas) {
        List<Heladera> heladerasCercanas = new ArrayList<>();
        Integer cantAux = 0;
        for (Heladera heladera : heladeras) {
            if (heladeraAfectada.getPuntoEstrategico().getAreas().equals(heladera.getPuntoEstrategico().getAreas())
                    && heladera.getActiva()) {
                heladerasCercanas.add(heladera);
                cantAux += 1;
                if (cantAux.equals(cantidadHeladerasCercanas)) {
                    break;
                }
            }
        }

        return heladerasCercanas;
    }

    public List<Heladera> obtenerHeladeras(Integer cantidadHeladeras) {
        List<Heladera> heladerasATraer = heladeras.subList(0, Math.min(5, heladeras.size()));
        return heladerasATraer;
    }
}
