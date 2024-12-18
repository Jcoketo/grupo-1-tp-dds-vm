package presentacion.gestorCuentas;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;


public class DarmeDeBajaController implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {

        String idPersona = context.sessionAttribute("idPersona");

        if (idPersona == null || idPersona.equals("")) {
            context.redirect("/logout");
        }



    }
}
