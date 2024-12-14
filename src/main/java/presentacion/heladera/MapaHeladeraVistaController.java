package presentacion.heladera;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.personas.TipoPersona;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class MapaHeladeraVistaController implements Handler {

    public MapaHeladeraVistaController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = context.sessionAttribute("model");
        if (model == null) {
            model = new HashMap<>();
            context.sessionAttribute("model", model);
        }
        TipoPersona tipoPersona = context.sessionAttribute("tipoPersona");
        model.put("logueado", context.sessionAttribute("logueado"));
        model.put("nombreUsuario", context.sessionAttribute("nombreUsuario"));


        if (tipoPersona == TipoPersona.PJ) {
            context.render("templates/mapaHeladerasJuridica.mustache", model);;
        } else {
            context.render("templates/mapaHeladeras.mustache",model);
        }



    }

}
