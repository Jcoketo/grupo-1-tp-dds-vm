package presentacion.heladera;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.personas.TipoPersona;
import org.jetbrains.annotations.NotNull;
import utils.GeneradorModel;

import java.util.HashMap;
import java.util.Map;

public class MapaHeladeraVistaController implements Handler {

    public MapaHeladeraVistaController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);


        model.put("logueado", context.sessionAttribute("logueado"));
        model.put("nombreUsuario", context.sessionAttribute("nombreUsuario"));
        model.put("esPersonaJuridica", context.sessionAttribute("tipoPersona") == TipoPersona.PJ);

        context.render("templates/mapaHeladeras.mustache",model);

    }

}
