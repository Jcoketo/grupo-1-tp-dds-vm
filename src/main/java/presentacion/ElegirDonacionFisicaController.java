package presentacion;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class ElegirDonacionFisicaController implements Handler {

    public ElegirDonacionFisicaController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {

        if (!Boolean.TRUE.equals(context.sessionAttribute("isLogged"))) {
            context.redirect("/login");
        }

        context.render("templates/elegirDonacionFisica.mustache");

    }
}
