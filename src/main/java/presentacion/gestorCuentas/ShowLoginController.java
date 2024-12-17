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

        if (context.sessionAttribute("errorLogin") != null) {
            model.put("errorLogin", context.sessionAttribute("errorLogin"));
            context.consumeSessionAttribute("errorLogin");
        }

        context.render("templates/login.mustache", model);
    }


}
