package presentacion.colaboraciones;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ElegirDonacionFisicaController implements Handler {

    public ElegirDonacionFisicaController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = new HashMap<>();
        context.render("templates/elegirDonacionFisica.mustache",model);

    }
}
