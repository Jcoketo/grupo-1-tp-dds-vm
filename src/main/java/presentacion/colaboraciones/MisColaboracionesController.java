package presentacion.colaboraciones;

import org.jetbrains.annotations.NotNull;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class MisColaboracionesController implements Handler{
    

public MisColaboracionesController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        context.render("templates/misColaboraciones.mustache");
    }

}
