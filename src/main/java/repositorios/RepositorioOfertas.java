package repositorios;

import colaboracion.Oferta;


import java.util.ArrayList;
import java.util.List;

public class RepositorioOfertas {
    private static RepositorioOfertas instancia = null;

    private static List<Oferta> ofertasDisponibles;
    private static List<Oferta> ofertasEntregadas;

    private RepositorioOfertas() {
        ofertasDisponibles = new ArrayList<Oferta>();
        ofertasEntregadas = new ArrayList<Oferta>();
    }

    public static RepositorioOfertas getInstancia() {
        if(instancia == null) {
            instancia = new RepositorioOfertas();
        }
        return instancia;
    }

    public void agregarOfertaDisponible(Oferta oferta) {
        ofertasDisponibles.add(oferta);
    }

    public void agregarOfertaEntregada(Oferta oferta) {
        ofertasEntregadas.add(oferta);
    }

    public List<Oferta> conocerOfertasDisponibles() {
        return ofertasDisponibles;
    }
}
