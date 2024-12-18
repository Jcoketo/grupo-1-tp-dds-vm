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

        String heladeraId = context.formParam("heladeraId");

        String comida1 = context.formParam("vianda1");
        String fechaCadu1 = context.formParam("fechaCaducidad1");
        String pesoVianda1 = context.formParam("pesoVianda1");
        String calorias1 = context.formParam("caloriasVianda1");
        String fechaElab1 = context.formParam("fechaElaboracion1");

        String comida2 = context.formParam("vianda2");
        String fechaCadu2 = context.formParam("fechaCaducidad2");
        String pesoVianda2 = context.formParam("pesoVianda2");
        String calorias2 = context.formParam("caloriasVianda2");
        String fechaElab2 = context.formParam("fechaElaboracion2");


        LocalDateTime fechaDonacion = LocalDateTime.now();

        Boolean validacion = validarVianda(context, heladeraId, comida1, fechaCadu1, pesoVianda1, calorias1, fechaElab1);
        if ( !validacion ){
            context.redirect("/donarVianda");
            return;
        }

        Boolean validacion2 = validarVianda(context, heladeraId, comida2, fechaCadu2, pesoVianda2, calorias2, fechaElab2);
        if ( !validacion2 ){
            context.redirect("/donarVianda");
            return;
        }

        try {
            Integer idPersona = context.sessionAttribute("idPersona");
            AuthServiceColaboracion.registrarColaboracionVianda(idPersona ,Integer.parseInt(heladeraId), comida1, fechaCadu1, pesoVianda1, calorias1, fechaDonacion);
            AuthServiceColaboracion.registrarColaboracionVianda(idPersona ,Integer.parseInt(heladeraId), comida2, fechaCadu2, pesoVianda2, calorias2, fechaDonacion);

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

    public static Boolean validarVianda(Context context, String heladeraId, String comida, String fechaCadu, String pesoVianda, String calorias, String fechaElab ) {

        if (heladeraId.equals("") || heladeraId == null || comida.equals("") || fechaCadu.equals("") ) {
            context.sessionAttribute("errorVianda", "Debe completar todos los campos");
            return Boolean.FALSE;
        }

        if ( ( !esNumerico(pesoVianda) && !pesoVianda.equals("") ) || ( !esNumerico(calorias) && !calorias.equals("") ) ) {
            context.sessionAttribute("errorVianda", "El peso y las calorías deben ser numéricos");
            return Boolean.FALSE;
        }

        LocalDate fechaElaboracion;
        LocalDate fechaCaducidad;

        try {
            fechaCaducidad = LocalDate.parse(fechaCadu);
            fechaElaboracion = LocalDate.parse(fechaElab);
        } catch (Exception e) {
            context.sessionAttribute("errorVianda", "Error en fecha de elaboracion / caducidad. Completela correctamente");
            return Boolean.FALSE;
        }

        if ( fechaElaboracion.isAfter(LocalDate.now()) ) {
            context.sessionAttribute("errorVianda", "La fecha de elaboración no puede ser posterior a la fecha actual");
            return Boolean.FALSE;
        }

        if (fechaElaboracion.isBefore(LocalDate.now().minusWeeks(2))) {
            context.sessionAttribute("errorVianda", "Revise los alimentos, pueden estar en mal estado");
            return Boolean.FALSE;
        }

        if (fechaCaducidad.isAfter(LocalDate.now().plusMonths(2))) {
            context.sessionAttribute("errorVianda", "La fecha de caducidad no puede ser mayor a 2 meses desde hoy");
            return Boolean.FALSE;
        }

        if (fechaCaducidad.isBefore(LocalDate.now())) {
            context.sessionAttribute("errorVianda", "La fecha de caducidad no puede ser anterior a hoy");
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

}
