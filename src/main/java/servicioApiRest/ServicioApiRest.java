package servicioApiRest;

import io.javalin.http.Handler;
import modelo.elementos.Heladera;
import modelo.elementos.PuntoEstrategico;
import org.jetbrains.annotations.NotNull;
import io.javalin.http.Context;
import persistencia.RepositorioHeladeras;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServicioApiRest implements Handler {

    private ServicioUbicacionHeladeras servicioUbicacionHeladeras;
    private RepositorioHeladeras repositorioHeladeras;

    public ServicioApiRest(RepositorioHeladeras repo) {
        super();
        this.repositorioHeladeras = repo;
        this.servicioUbicacionHeladeras = new ServicioUbicacionHeladeras(repo);
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        double latitud= Double.parseDouble(context.queryParam("lat"));
        double longitud = Double.parseDouble(context.queryParam("long"));

        Double radio = 10.0;

        // Llamada al servicio de ubicación para obtener heladeras cercanas
        List<PuntoEstrategico> puntosHeladerasCercanas = servicioUbicacionHeladeras.obtenerHeladerasCercanas(latitud, longitud, radio);

        // Enviar la respuesta en formato JSON
        context.json(puntosHeladerasCercanas);

    }

}
