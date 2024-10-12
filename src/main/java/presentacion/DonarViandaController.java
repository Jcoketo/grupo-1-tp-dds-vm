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
        String name = context.queryParam("nombre");
        String address = context.queryParam("direccion");
        String status = context.queryParam("estado");
        String availability = context.queryParam("disponibilidad");

        Map<String, Object> model = new HashMap<>();
        model.put("nombre", name);
        model.put("direccion", address);
        model.put("estado", status);
        model.put("disponibilidad", availability);

        context.render("templates/donarVianda.mustache", model);
    }
}
