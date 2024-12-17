package presentacion.vistaTecnico;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.Getter;
import lombok.Setter;
import modelo.elementos.Incidente;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioIncidentes;
import persistencia.RepositorioVisitas;
import utils.GeneradorModel;

import java.util.List;
import java.util.Map;

public class RegistrarVisitaTecnicosController implements Handler {

    private final RepositorioIncidentes repoIncidentes = RepositorioIncidentes.getInstancia();

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        if ( context.sessionAttribute("notificacionVisita") != null ) {
            model.put("notificacionVisita", context.sessionAttribute("notificacionVisita"));
            context.consumeSessionAttribute("notificacionVisita");
        }


        // Obtener los incidentes no solucionados
        List<Incidente> incidentes = repoIncidentes.obtenerIncidentesNoSolucionados();

        // Crear el listado de heladeras con sus incidentes correspondientes
        List<HeladeraIncidentes> heladerasConIncidentes = incidentes.stream()
                .map(Incidente::getHeladera) // Obtener heladeras de incidentes
                .distinct() // Evitar duplicados
                .map(heladera -> {
                    // Crear objeto HeladeraIncidentes
                    HeladeraIncidentes heladeraIncidentes = new HeladeraIncidentes();
                    heladeraIncidentes.setId(heladera.getId());
                    heladeraIncidentes.setNombre(heladera.getNombre());
                    heladeraIncidentes.setIncidentes(
                            incidentes.stream()
                                    .filter(i -> i.getHeladera().getId() == heladera.getId()) // Filtrar incidentes por heladera
                                    .toList()
                    );
                    return heladeraIncidentes;
                })
                .toList();

        // Agregar notificaciÃ³n si existe
        if (context.sessionAttribute("notificacionVisita") != null) {
            model.put("notificacionVisita", context.sessionAttribute("notificacionVisita"));
            context.consumeSessionAttribute("notificacionVisita");
        }

        // Agregar heladeras al modelo
        model.put("heladeras", heladerasConIncidentes);

        // Renderizar la plantilla
        context.render("templates/registrarVisita.mustache", model);
    }
}

@Getter
@Setter
class HeladeraIncidentes {
    private int id;
    private String nombre;
    private List<Incidente> incidentes;
}