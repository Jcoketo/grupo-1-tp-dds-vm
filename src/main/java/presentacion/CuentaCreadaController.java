package presentacion;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class CuentaCreadaController implements Handler {


    @Override
    public void handle(@NotNull Context context) throws Exception {
        context.render("templates/cuentaCreada.mustache");
    }
}
