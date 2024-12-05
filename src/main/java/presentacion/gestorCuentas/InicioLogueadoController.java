package presentacion.gestorCuentas;

import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class InicioLogueadoController implements Handler {

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = context.sessionAttribute("model");
        if (model == null) {
            model = new HashMap<>();
            context.sessionAttribute("model", model);
        }
        model.put("nombreUsuario", context.sessionAttribute("nombreUsuario"));
        context.render("templates/inicio.mustache", model);
    }

}   