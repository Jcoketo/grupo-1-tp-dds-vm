package presentacion.gestorCuentas;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

public class CrearCuentaFisicaSSOController implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = new HashMap<>();

        String nombre = context.sessionAttribute("auth0Nombre");
        String email = context.sessionAttribute("auth0Email");
        String apellido = context.sessionAttribute("auth0Apellido");

        model.put("nombre", nombre);
        model.put("email", email);
        model.put("apellido", apellido);

        context.render("templates/crearCuentaFisicaSSO.mustache", model);
    }
}
