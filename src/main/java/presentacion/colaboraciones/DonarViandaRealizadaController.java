package presentacion.colaboraciones;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.authService.AuthServiceColaboracion;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioHeladeras;
import utils.GeneradorModel;

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
        Map<String, Object> model = GeneradorModel.getModel(context);

        String heladeraId = context.formParam("heladeraId");
        String comida = context.formParam("vianda1");
        String fechaCadu = context.formParam("fechaCaducidad1");
        String pesoVianda = context.formParam("pesoVianda1");
        String calorias = context.formParam("caloriasVianda1");
        String fechaElab = context.formParam("fechaElaboracion1");
        LocalDateTime fechaDonacion = LocalDateTime.now();

        //TODO falto hacerlo con la segunda vianda

        if (heladeraId.equals("") || comida.equals("") || fechaCadu.equals("") ) {
            context.sessionAttribute("errorVianda", "Debe completar todos los campos");
            context.redirect("/donarVianda");
            return;
        }

        if ( ( !esNumerico(pesoVianda) && !pesoVianda.equals("") ) || ( !esNumerico(calorias) && !calorias.equals("") ) ) {
            context.sessionAttribute("errorVianda", "El peso y las calorías deben ser numéricos");
            context.redirect("/donarVianda");
            return;
        }

        LocalDate fechaElaboracion;
        LocalDate fechaCaducidad;

        try {
            fechaCaducidad = LocalDate.parse(fechaCadu);
            fechaElaboracion = LocalDate.parse(fechaElab);
        } catch (Exception e) {
            context.sessionAttribute("errorVianda", "Error en fecha de elaboracion / caducidad. Completela correctamente");
            context.redirect("/donarVianda");
            return;
        }

        if ( fechaElaboracion.isAfter(LocalDate.now()) ) {
            context.sessionAttribute("errorVianda", "La fecha de elaboración no puede ser posterior a la fecha actual");
            context.redirect("/donarVianda");
            return;
        }

        if (fechaElaboracion.isBefore(LocalDate.now().minusWeeks(2))) {
            context.sessionAttribute("errorVianda", "La fecha de elaboración no puede ser mayor a 2 semanas atrás");
            context.redirect("/donarVianda");
            return;
        }

        if (fechaCaducidad.isAfter(LocalDate.now().plusMonths(2))) {
            context.sessionAttribute("errorVianda", "La fecha de caducidad no puede ser mayor a 2 meses desde hoy");
            context.redirect("/donarVianda");
            return;
        }

        if (fechaCaducidad.isBefore(LocalDate.now())) {
            context.sessionAttribute("errorVianda", "La fecha de caducidad no puede ser anterior a hoy");
            context.redirect("/donarVianda");
            return;
        }

        try {
            Integer idPersona = context.sessionAttribute("idPersona");
            AuthServiceColaboracion.registrarColaboracionVianda(idPersona ,Integer.parseInt(heladeraId), comida, fechaCadu, Integer.parseInt(pesoVianda), Integer.parseInt(calorias), fechaDonacion);

        } catch (Exception e) {
            context.sessionAttribute("errorVianda", e.getMessage());
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
