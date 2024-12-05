package presentacion.incidentes;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioIncidentes;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ReportarFallaTecnicaFinalizadaController implements Handler {

    private RepositorioIncidentes repoFallas;

    public ReportarFallaTecnicaFinalizadaController(RepositorioIncidentes repoIncidentes) {
        super();
        this.repoFallas = repoIncidentes;
    }


    @Override
    public void handle(@NotNull Context context) throws Exception {
        String titulo = Objects.requireNonNull(context.formParam("titulo"));
        String descripcion = Objects.requireNonNull(context.formParam("descripcion"));
        // foto??
        Map<String, Object> model = context.sessionAttribute("model");
        if (model == null) {
            model = new HashMap<>();
            context.sessionAttribute("model", model);
        }
        model.put("nombreUsuario", context.sessionAttribute("nombreUsuario"));
        context.render("templates/aceptarReportarFalla.mustache", model);
        context.redirect("/aceptarReportarFalla");
    }

}
