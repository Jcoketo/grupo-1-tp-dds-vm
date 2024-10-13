package presentacion.gestorCuentas;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class CrearCuentaFisicaController implements Handler {

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = new HashMap<>();
        context.render("templates/crearCuentaFisica.mustache", model);



        // aca hay que ir a dar de alta e usuario en la base de datos
        //TODO


    }
}
