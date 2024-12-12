package presentacion.colaboraciones;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.authService.AuthServiceColaboracion;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioHeladeras;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class DonarViandaRealizadaController implements Handler {

    public DonarViandaRealizadaController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = context.sessionAttribute("model");
        if (model == null) {
            model = new HashMap<>();
            context.sessionAttribute("model", model);
        }

        String heladeraId = context.formParam("heladeraId");
        String comida = context.formParam("vianda1");
        String fechaCaducidad = context.formParam("fechaCaducidad1");
        String pesoVianda = context.formParam("pesoVianda1");
        String calorias = context.formParam("caloriasVianda1");
        LocalDateTime fechaDonacion = LocalDateTime.now();

        //TODO falto hacerlo con la segunda vianda

        if (heladeraId.equals("") || comida.equals("") || fechaCaducidad.equals("") || pesoVianda.equals("") || calorias.equals("")) {
            model.put("errorVianda", "Debe completar todos los campos");
            //context.status(400);
            context.redirect("/donarVianda");
            return;
        }

        if (!esNumerico(pesoVianda) || !esNumerico(calorias)) {
            model.put("errorVianda", "El peso y las calorías deben ser numéricos");
            //context.status(400);
            context.redirect("/donarVianda");
            return;
        }

        try {
            Integer idPersona = context.sessionAttribute("idPersona");
            AuthServiceColaboracion.registrarColaboracionVianda(idPersona ,Integer.parseInt(heladeraId), comida, fechaCaducidad, Integer.parseInt(pesoVianda), Integer.parseInt(calorias), fechaDonacion);

        } catch (Exception e) {
            model.put("errorVianda", e.getMessage());
            //context.status(400);
            context.redirect("/donarVianda");
            return;
        }

        context.redirect("/graciasPorDonar");
    }

    public static boolean esNumerico(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
