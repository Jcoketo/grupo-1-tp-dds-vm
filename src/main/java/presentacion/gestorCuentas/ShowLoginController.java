package presentacion.gestorCuentas;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import utils.GeneradorModel;

import java.util.Map;

public class ShowLoginController implements Handler {

    public ShowLoginController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        String mensaje = context.sessionAttribute("errorLogin");
        if (mensaje != null) {
            model.put("errorLogin", mensaje);
            context.consumeSessionAttribute("errorLogin");
        }

        context.render("templates/login.mustache", model);
    }


}
