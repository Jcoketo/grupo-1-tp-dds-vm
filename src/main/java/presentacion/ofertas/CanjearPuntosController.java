package presentacion.ofertas;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class CanjearPuntosController implements Handler {

    public CanjearPuntosController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        context.render("templates/canjearPuntos.mustache");
    }


}
