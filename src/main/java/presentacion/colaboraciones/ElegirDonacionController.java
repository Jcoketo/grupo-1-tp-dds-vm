package presentacion.colaboraciones;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.personas.TipoPersona;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ElegirDonacionController implements Handler {


    @Override
    public void handle(@NotNull Context context) throws Exception {

        if (Objects.equals(context.sessionAttribute("logueado"), true)) {
            TipoPersona tipoPersona = context.sessionAttribute("tipoPersona");
            if (tipoPersona == TipoPersona.PJ) {
                context.redirect("/elegirDonacionJuridica");
            } else {
                context.redirect("/elegirDonacionFisica");
            }
        } else {
            context.redirect("/login");
        }
    }
}
