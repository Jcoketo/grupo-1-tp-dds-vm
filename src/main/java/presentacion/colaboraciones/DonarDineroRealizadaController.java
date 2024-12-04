package presentacion.colaboraciones;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.authService.AuthServiceColaboracion;
import modelo.excepciones.ExcepcionValidacion;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;


public class DonarDineroRealizadaController implements Handler {

    public DonarDineroRealizadaController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {

        Map<String, Object> model = context.sessionAttribute("model");
        if (model == null) {
            model = new java.util.HashMap<>();
            context.sessionAttribute("model", model);
        }

        String monto = context.formParam("monto");
        String nombreTarj = context.formParam("nombreTarj");
        String numTarj = context.formParam("numTarj");
        String mesExpir = context.formParam("mesExpir");
        String anioExpir = context.formParam("anioExpir");
        String codigoSeguridad = context.formParam("cvv");

        String fechaExpir = mesExpir + "/" + anioExpir;

        if ( !esNumerico(monto) || !esNumerico(numTarj) || !esNumerico(codigoSeguridad) )   {
            model.put("errorDonacion", "Los datos ingresados no son correctos");
            context.status(400);
            context.redirect("/donarDinero");
            return;
        }
        if ( nombreTarj.isEmpty()) {
            model.put("errorDonacion", "El nombre del titular debe estar completo");
            context.status(400);
            context.redirect("/donarDinero");
            return;
        }
        if ( numTarj.length() != 16) {
            model.put("errorDonacion", "El número de tarjeta es erroneo");
            context.status(400);
            context.redirect("/donarDinero");
            return;
        }
        if ( codigoSeguridad.length() != 3) {
            model.put("errorDonacion", "El código de seguridad es erroneo");
            context.status(400);
            context.redirect("/donarDinero");
            return;
        }

        try {
            // TODA LA LOGICA DE IR AL BANCO Y DESCONTAR EL DINERO
            Integer idPersona = context.sessionAttribute("idPersona");
            AuthServiceColaboracion.registrarColaboracionDinero(idPersona, monto);
        } catch (ExcepcionValidacion e) {
            model.put("errorDonacion", "No se pudo realizar la donación");
            context.status(400);
            context.redirect("/donarDinero");
            return;
        }

        context.redirect("/graciasPorDonar");


    }
    public static boolean esNumerico(String str) {
        if (str == null || str.isEmpty()) { // TODOS OBLIGATORIOS
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
