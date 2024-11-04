package servicioApiRest;

import modelo.elementos.Heladera;
import modelo.elementos.PuntoEstrategico;
import persistencia.RepositorioHeladeras;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ServicioUbicacionHeladeras {

    private RepositorioHeladeras repositorioHeladeras;

    public ServicioUbicacionHeladeras(RepositorioHeladeras repositorioHeladeras) {
        this.repositorioHeladeras = repositorioHeladeras;
    }

    // Método de Haversine para calcular la distancia en km
    private double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        final int RADIO_TIERRA = 6371; // Radio de la Tierra en km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return RADIO_TIERRA * c;
    }

    // Método para obtener heladeras cercanas
    public List<PuntoEstrategico> obtenerHeladerasCercanas(double latitud, double longitud, double radio) {
        return repositorioHeladeras.getHeladeras().stream()
                .filter(Heladera::getActiva) // Solo heladeras activas
                .filter(heladera -> {
                    double distancia = calcularDistancia(
                            latitud,
                            longitud,
                            heladera.getPuntoEstrategico().getLatitud(),
                            heladera.getPuntoEstrategico().getLongitud()
                    );
                    return distancia <= radio;
                })
                .sorted(Comparator.comparingDouble(heladera -> calcularDistancia(
                        latitud,
                        longitud,
                        heladera.getPuntoEstrategico().getLatitud(),
                        heladera.getPuntoEstrategico().getLongitud()
                ))) // Ordenar por distancia
                .map(Heladera::getPuntoEstrategico) // Transformar Heladera a PuntoEstrategico
                .collect(Collectors.toList());
    }
}
