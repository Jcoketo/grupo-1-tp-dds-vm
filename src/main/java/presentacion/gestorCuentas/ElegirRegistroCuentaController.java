package presentacion.gestorCuentas;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ElegirRegistroCuentaController implements Handler {

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = new HashMap<>();

        model.put("registroSSO", context.sessionAttribute("registroAuth0"));

        context.render("templates/elegirRegistroCuenta.mustache", model);
    }
}
