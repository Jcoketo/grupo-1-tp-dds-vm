package presentacion.reportes;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class MisReportesController implements Handler {

    public MisReportesController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        context.render("templates/misReportes.mustache");
    }
}

