package presentacion.colaboraciones;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import utils.GeneradorModel;

import java.util.HashMap;
import java.util.Map;

public class RegistroPersonaVulnerableFinalController implements Handler{

    public RegistroPersonaVulnerableFinalController() {
        super();
    }

    @Override
        public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);
        context.render("templates/registroPersonaVulnerableFinal.mustache", model);
    }
}

