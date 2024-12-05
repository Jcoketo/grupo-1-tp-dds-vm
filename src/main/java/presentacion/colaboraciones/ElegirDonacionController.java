package presentacion.colaboraciones;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.personas.TipoPersona;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ElegirDonacionController implements Handler {


    @Override
    public void handle(@NotNull Context context) throws Exception {

        if (Objects.equals(context.sessionAttribute("logueado"), true)) {
            Map<String, Object> model = context.sessionAttribute("model");
            if (model == null) {
                model = new HashMap<>();
                context.sessionAttribute("model", model);
            }
            TipoPersona tipoPersona = context.sessionAttribute("tipoPersona");
            model.put("nombreUsuario", context.sessionAttribute("nombreUsuario"));

            if (tipoPersona == TipoPersona.PJ) {
                context.render("templates/elegirDonacionJuridica.mustache",model);
            } else {
                context.render("templates/elegirDonacionFisica.mustache",model);
            }
        } else {
            context.redirect("/login");
        }
    }
}
