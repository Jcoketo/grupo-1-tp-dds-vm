package presentacion.vistasAdmin;

import accessManagment.Roles;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class CargarSCVController implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = context.sessionAttribute("model");
        if (model == null) {
            model = new java.util.HashMap<>();
            context.sessionAttribute("model", model);
        }

        if (context.sessionAttribute("logueado") != Boolean.TRUE) {
            context.redirect("/login");
            return;
        }
        if (context.sessionAttribute("rolUsuario") != Roles.ADMIN) {
            context.redirect("/404Error");
            return;
        }

        model.put("nombreUsuario", context.sessionAttribute("nombreUsuario"));
        model.put("logueado", context.sessionAttribute("logueado"));

        context.render("templates/cargarSCV.mustache", model);
    }
}
