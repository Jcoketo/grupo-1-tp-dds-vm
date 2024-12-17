package presentacion.vistaTecnico;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import utils.GeneradorModel;

import java.util.Map;

public class RegistrarTecnicoController implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        if ( context.sessionAttribute("errorRegistroTecnico") != null ) {
            model.put("errorRegistroTecnico", context.sessionAttribute("errorRegistroTecnico"));
            context.consumeSessionAttribute("errorRegistroTecnico");
        }


        context.render("templates/registroTecnico.mustache", model);
    }
}
