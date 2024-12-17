package presentacion.colaboraciones;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import utils.GeneradorModel;

import java.util.HashMap;
import java.util.Map;

public class RegistroPersonaVulnerableController implements Handler {

    public RegistroPersonaVulnerableController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        if (context.sessionAttribute("errorRegistroVulnerable") != null) {
            model.put("errorRegistroVulnerable", context.sessionAttribute("errorRegistroVulnerable"));
            context.consumeSessionAttribute("errorRegistroVulnerable");
        }

        context.render("templates/registroPersonaVulnerable.mustache", model);
    }
}
