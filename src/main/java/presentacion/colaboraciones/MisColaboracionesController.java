package presentacion.colaboraciones;

import org.jetbrains.annotations.NotNull;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import persistencia.RepositorioColaboradores;

import java.util.HashMap;
import java.util.Map;

public class MisColaboracionesController implements Handler{

    private RepositorioColaboradores repoColaboradores;
    

public MisColaboracionesController(RepositorioColaboradores repoColaboradores) {
        super();
        this.repoColaboradores = repoColaboradores;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = context.sessionAttribute("model");
        if (model == null) {
            model = new HashMap<>();
            context.sessionAttribute("model", model);
        }

        Integer idPersona = context.sessionAttribute("idPersona");

        model.put("colaboraciones", repoColaboradores.getColaboraciones((idPersona)));
        context.render("templates/misColaboraciones.mustache");
    }

}
