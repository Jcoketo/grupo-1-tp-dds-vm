package presentacion.colaboraciones;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class DonarDistribucionViandaController implements Handler {

    public DonarDistribucionViandaController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> modelo = new HashMap<>();

        String heladera1 = context.queryParam("heladera1");
        String direccion1 = context.queryParam("direccion1");
        String estado1 = context.queryParam("estado1");
        String disponibilidad1 = context.queryParam("disponibilidad1");

        String heladera2 = context.queryParam("heladera2");
        String direccion2 = context.queryParam("direccion2");
        String estado2 = context.queryParam("estado2");
        String disponibilidad2 = context.queryParam("disponibilidad2");

        modelo.put("heladera1", heladera1);
        modelo.put("direccion1", direccion1);
        modelo.put("estado1", estado1);
        modelo.put("disponibilidad1", disponibilidad1);

        modelo.put("heladera2", heladera2);
        modelo.put("direccion2", direccion2);
        modelo.put("estado2", estado2);
        modelo.put("disponibilidad2", disponibilidad2);


        context.render("templates/donarDistribuirViandas.mustache", modelo);

    }
}
