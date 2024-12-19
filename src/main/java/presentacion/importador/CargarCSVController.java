package presentacion.importador;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import utils.GeneradorModel;

import java.util.Map;

public class CargarCSVController implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        if ( context.sessionAttribute("cargaMasiva") != null) {
            model.put("cargaMasiva", context.sessionAttribute("cargaMasiva"));
            context.consumeSessionAttribute("cargaMasiva");
        }

        context.render("templates/cargarCSV.mustache", model);
    }
}
