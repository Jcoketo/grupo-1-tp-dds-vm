package presentacion.heladera;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class MapaHeladerasDistribucionDestinoController implements Handler{

    public MapaHeladerasDistribucionDestinoController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = new HashMap<>();

        String heladera1 = context.queryParam("heladera1");
        String direccion1 = context.queryParam("direccion1");
        String estado1 = context.queryParam("estado1");
        String disponibilidad1 = context.queryParam("disponibilidad1");

        String heladera2 = context.queryParam("heladera2");
        String direccion2 = context.queryParam("direccion2");
        String estado2 = context.queryParam("estado2");
        String disponibilidad2 = context.queryParam("disponibilidad2");

        model.put("heladera1", heladera1);
        model.put("direccion1", direccion1);
        model.put("estado1", estado1);
        model.put("disponibilidad1", disponibilidad1);

        model.put("heladera2", heladera2);
        model.put("direccion2", direccion2);
        model.put("estado2", estado2);
        model.put("disponibilidad2", disponibilidad2);


        Boolean estaLogueado = context.sessionAttribute("logueado");

        //if( estaLogueado == null ){ estaLogueado = false; }
        model.put("logueado", estaLogueado != null && estaLogueado);

        context.render("templates/mapaHeladerasDistribucionDestino.mustache", model);

    }
}
