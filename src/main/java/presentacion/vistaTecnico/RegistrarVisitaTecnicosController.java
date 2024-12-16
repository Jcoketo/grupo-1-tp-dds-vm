package presentacion.vistaTecnico;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.Getter;
import lombok.Setter;
import modelo.elementos.Heladera;
import modelo.elementos.Incidente;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioHeladeras;
import persistencia.RepositorioIncidentes;
import persistencia.RepositorioVisitas;
import utils.GeneradorModel;

import java.util.List;
import java.util.Map;

public class RegistrarVisitaTecnicosController implements Handler {

    private RepositorioIncidentes repoIncidentes = RepositorioIncidentes.getInstancia();
    private RepositorioVisitas repoVisitas = RepositorioVisitas.getInstancia();

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        model.put("nombreUsuario", context.sessionAttribute("nombreUsuario"));

        List<Incidente> incidentes = repoIncidentes.obtenerIncidentesNoSolucionados();

        List<Heladera> heladera = incidentes.stream().map(Incidente::getHeladera).toList();

        List<HeladeraIncidentes> heladeraIncidentes = heladera.stream().map(h -> {
            HeladeraIncidentes heladeraIncidentes1 = new HeladeraIncidentes();
            heladeraIncidentes1.setId(h.getId());
            heladeraIncidentes1.setNombre(h.getNombre());
            heladeraIncidentes1.setIncidentes(incidentes.stream().filter(i -> i.getHeladera().getId() == h.getId()).toList());
            return heladeraIncidentes1;
        }).toList();

        model.put("heladeras", heladeraIncidentes);

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
