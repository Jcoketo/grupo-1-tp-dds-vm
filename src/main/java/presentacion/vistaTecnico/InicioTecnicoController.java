package presentacion.vistaTecnico;

import accessManagment.Roles;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import utils.GeneradorModel;

import java.util.Map;

public class InicioTecnicoController implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        if (context.sessionAttribute("logueado") != Boolean.TRUE) {
            context.redirect("/login");
            return;
        }

        Roles rol = context.sessionAttribute("rolUsuario");

        if (rol != Roles.TECNICO) {
            context.redirect("/404Error");
            return;
        }

        if (context.sessionAttribute("notificacionVisita") != null) {
            model.put("notificacionVisita", context.sessionAttribute("notificacionVisita"));
            context.consumeSessionAttribute("notificacionVisita");
        }

        context.render("templates/inicioTecnico.mustache", model);
    }
}
