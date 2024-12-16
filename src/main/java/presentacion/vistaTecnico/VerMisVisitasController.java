package presentacion.vistaTecnico;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.personas.Visita;
import persistencia.RepositorioVisitas;
import utils.GeneradorModel;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

public class VerMisVisitasController implements Handler {

    private RepositorioVisitas repoVisitas = RepositorioVisitas.getInstancia();

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        Integer idTecnico = context.sessionAttribute("idTecnico");

        try {
            List<Visita> visitas = repoVisitas.getVisitasPorTecnico(idTecnico);
            model.put("visitas", visitas);
            context.render("templates/verMisVisitas.mustache", model);
        } catch (Exception e) {
            model.put("error", e.getMessage());
            context.render("templates/error.mustache", model);
        }
    }
}
