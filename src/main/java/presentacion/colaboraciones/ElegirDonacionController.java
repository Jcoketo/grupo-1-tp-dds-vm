package presentacion.colaboraciones;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.personas.TipoPersona;
import org.jetbrains.annotations.NotNull;
import utils.GeneradorModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ElegirDonacionController implements Handler {


    @Override
    public void handle(@NotNull Context context) throws Exception {

        if (Objects.equals(context.sessionAttribute("logueado"), true)) {
            Map<String, Object> model = GeneradorModel.getModel(context);
            TipoPersona tipoPersona = context.sessionAttribute("tipoPersona");

            NotificacionTarjeta notificacionTarjeta = context.sessionAttribute("notificacionTarjeta");
            if(notificacionTarjeta != null){
                model.put("notificacionTarjeta", notificacionTarjeta);
            }
            context.consumeSessionAttribute("notificacionTarjeta");

            if (tipoPersona == TipoPersona.PJ) {
                context.render("templates/elegirDonacionJuridica.mustache",model);
            } else {
                context.render("templates/elegirDonacionFisica.mustache",model);
            }
        } else {
            context.redirect("/login");
        }
    }
}
