package presentacion;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class InicioLogeadoController implements Handler {

    @Override
    public void handle(@NotNull Context context) throws Exception {

        //Validar.validarLogueado(context,"/inicio-sinLog");

        if (Boolean.FALSE.equals(context.sessionAttribute("logueado"))) {
            context.redirect("/inicio-sinLog");
        }

        Map<String, Object> model = new HashMap<>();
        context.render("templates/inicioLogeado.mustache", model);
    }
}
