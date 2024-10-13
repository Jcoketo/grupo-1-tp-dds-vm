package presentacion;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioIncidentes;
import persistencia.RepositorioPersonasVulnerables;

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

        context.redirect("/aceptarReportarFalla");
    }
}
