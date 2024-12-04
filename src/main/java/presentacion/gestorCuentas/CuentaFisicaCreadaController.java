package presentacion.gestorCuentas;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.authService.AuthServiceColaborador;
import modelo.excepciones.ExcepcionValidacion;
import modelo.authService.AuthServiceUsuario;
import modelo.personas.TipoDocumento;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CuentaFisicaCreadaController implements Handler {

    public CuentaFisicaCreadaController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = new HashMap<>();
        context.sessionAttribute("model", model);

        String tipoDoc = context.formParam("tipoDoc");
        String nroDoc = context.formParam("nroDoc");
        String nombre = context.formParam("nombre");
        String apellido = context.formParam("apellido");
        String email = context.formParam("email"); // Este va a ser el Medio de contacto necesario y x defecto
        String password = context.formParam("password");
        String terminos = context.formParam("terms");
        String username = context.formParam("username");
        String telefono = context.formParam("telefono"); // opcional
        String direccion = context.formParam("direccion"); // opcional
        // FECHA DE NACIMIENTO
        String dia = context.formParam("dia"); // opcional
        String mes = context.formParam("mes"); // opcional
        String anio = context.formParam("anio"); // opcional
        if ( Integer.parseInt(dia) < 10 ) {
            dia = "0" + dia;
        }
        String fechaNacimiento = anio + "-" + mes + "-" + dia; // opcional

        if (nombre.equals("") || apellido.equals("") || email.equals("") ||
                password.equals("") || terminos.equals("") || username.equals("") ||
                tipoDoc.equals("") || nroDoc.equals("") )  {
            model.put("error", "Debe completar los campos obligatorios");
            //context.status(400);
            context.redirect("/crearCuentaFisica");
            return;
        }

        if ( !esNumerico(nroDoc)  ||  !esNumerico(telefono) )  {
            model.put("error", "El número de documento o el teléfono no son numéricos");
            //context.status(400);
            context.redirect("/crearCuentaFisica");
            return;
        }

        if ( !nroDoc.matches("[0-9]{0,8}") )  {
            model.put("error", "El número de documento debe tener 8 dígitos");
            //context.status(400);
            context.redirect("/crearCuentaFisica");
            return;
        }

        if ( !telefono.equals("")  &&  !telefono.matches("[0-9]{8,10}") )  {
            model.put("error", "El teléfono debe tener entre 8 y 10 dígitos");
            //context.status(400);
            context.redirect("/crearCuentaFisica");
            return;
        }
        TipoDocumento tipoDocumentoEnum;
        try {
            tipoDoc = tipoDoc.toUpperCase();
            tipoDocumentoEnum = TipoDocumento.valueOf(tipoDoc);
        } catch (IllegalArgumentException e) {
            model.put("error", "El tipo de documento no es válido");
            //context.status(400);
            context.redirect("/crearCuentaFisica");
            return;
        }


        try {
            AuthServiceUsuario.registrarUsuario(email, username, password);
            AuthServiceColaborador.registrarColaboradorFisico(tipoDocumentoEnum, nroDoc, nombre, apellido, email, telefono, direccion, fechaNacimiento);

        } catch (ExcepcionValidacion e) {
            // TODO ROLLBACK
            model.put("error", e.getMessage());
            //context.status(400);
            context.redirect("/crearCuentaFisica");
            return;
        }

        context.redirect("/cuentaCreada");
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
