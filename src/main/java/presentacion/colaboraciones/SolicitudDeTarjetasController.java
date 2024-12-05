package presentacion.colaboraciones;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.excepciones.ExcepcionValidacion;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioColaboradores;

import java.util.HashMap;
import java.util.Map;

public class SolicitudDeTarjetasController implements Handler {

    private RepositorioColaboradores repoColab;

    public SolicitudDeTarjetasController(RepositorioColaboradores repo) {
        super();
        this.repoColab = repo;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = context.sessionAttribute("model");
        if (model == null) {
            model = new HashMap<>();
            context.sessionAttribute("model", model);
        }

        Integer IdPersona = context.sessionAttribute("IdPersona");

        try {



        } catch (ExcepcionValidacion e) {
            e.getMessage();
            context.redirect("/error");
        }
    }
}
