package presentacion;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DonarViandaController implements Handler {

    @Override
    public void handle(@NotNull Context context) throws Exception {
            Map<String, Object> model = new HashMap<>();

            String nombre = context.queryParam("nombre");
            String direccion = context.queryParam("direccion");
            String estado = context.queryParam("estado");
            String disponibilidad = context.queryParam("disponibilidad");

            model.put("nombre", nombre);
            model.put("direccion", direccion);
            model.put("estado", estado);
            model.put("disponibilidad", disponibilidad);

            context.render("templates/donarVianda.mustache",model);
    }
}
