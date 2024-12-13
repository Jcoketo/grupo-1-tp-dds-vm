package presentacion;

import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import utils.GeneradorModel;

public class InicioController implements Handler {

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        model.put("logueado", context.sessionAttribute("logueado"));
        model.put("nombreUsuario", context.sessionAttribute("nombreUsuario"));


        context.render("templates/inicio.mustache", model);
    }
}
