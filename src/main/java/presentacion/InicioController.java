package presentacion;

import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class InicioController implements Handler {

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = new HashMap<>();
        Boolean estaLogueado = context.sessionAttribute("logueado");
        //if( estaLogueado == null ){ estaLogueado = false; }
        model.put("logueado", estaLogueado != null && estaLogueado);
        model.put("nombreUsuario", context.sessionAttribute("nombreUsuario"));
        context.render("templates/inicio.mustache", model);
    }
}
