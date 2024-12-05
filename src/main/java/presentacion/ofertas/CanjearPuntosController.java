package presentacion.ofertas;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class CanjearPuntosController implements Handler {

    public CanjearPuntosController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = context.sessionAttribute("model");
        if (model == null) {
            model = new HashMap<>();
            context.sessionAttribute("model", model);
        }
        model.put("nombreUsuario", context.sessionAttribute("nombreUsuario"));
        context.render("templates/canjearPuntos.mustache", model);
    }

}
