package presentacion.vistaAdmin;

import accessManagment.Roles;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import utils.GeneradorModel;

import java.util.Map;

public class inicioADMINController implements Handler {


    @Override
    public void handle(@NotNull Context context) throws Exception {

        Map<String, Object> model = GeneradorModel.getModel(context);

        if (context.sessionAttribute("logueado") != Boolean.TRUE) {
            context.redirect("/login");
            return;
        }

        Roles rol = context.sessionAttribute("rolUsuario");

        if (rol != Roles.ADMIN) {
            context.redirect("/404Error");
            return;
        }

        model.put("nombreUsuario", context.sessionAttribute("nombreUsuario"));
        model.put("logueado", context.sessionAttribute("logueado"));

        context.render("templates/inicioAdmin.mustache", model);
    }
}
