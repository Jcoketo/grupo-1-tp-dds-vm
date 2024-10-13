package presentacion;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioIncidentes;

import java.util.HashMap;
import java.util.Map;

public class ReportarFallaTecnicaController implements Handler {

    public ReportarFallaTecnicaController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = new HashMap<>();
        context.render("templates/reportarFallaTecnica.mustache", model);
    }
}
