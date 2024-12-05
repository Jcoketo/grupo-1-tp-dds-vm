package presentacion.heladera;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class MapaHeladerasDistribucionOrigenController implements Handler {

    public MapaHeladerasDistribucionOrigenController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = context.sessionAttribute("model");
        if (model == null) {
            model = new HashMap<>();
            context.sessionAttribute("model", model);
        }

        String idHeladeraOrigen = context.queryParam("idHeladeraOrigen");
        String heladera1 = context.queryParam("heladera1");
        String direccion1 = context.queryParam("direccion1");
        String estado1 = context.queryParam("estado1");
        String disponibilidad1 = context.queryParam("disponibilidad1");

        String idHeladeraDestino = context.queryParam("idHeladeraDestino");
        String heladera2 = context.queryParam("heladera2");
        String direccion2 = context.queryParam("direccion2");
        String estado2 = context.queryParam("estado2");
        String disponibilidad2 = context.queryParam("disponibilidad2");

        model.put("idHeladeraOrigen", context.queryParam("idHeladeraOrigen"));
        model.put("heladera1", heladera1);
        model.put("direccion1", direccion1);
        model.put("estado1", estado1);
        model.put("disponibilidad1", disponibilidad1);

        model.put("idHeladeraDestino", context.queryParam("idHeladeraDestino"));
        model.put("heladera2", heladera2);
        model.put("direccion2", direccion2);
        model.put("estado2", estado2);
        model.put("disponibilidad2", disponibilidad2);

        model.put("nombreUsuario", context.sessionAttribute("nombreUsuario"));

        /*Boolean estaLogueado = context.sessionAttribute("logueado");

        //if( estaLogueado == null ){ estaLogueado = false; }
        model.put("logueado", estaLogueado != null && estaLogueado);*/

        context.render("templates/mapaHeladerasDistribucionOrigen.mustache", model);

    }
}
