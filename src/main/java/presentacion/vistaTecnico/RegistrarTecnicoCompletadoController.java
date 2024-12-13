package presentacion.vistaTecnico;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.authService.AuthServiceTecnico;
import modelo.excepciones.ExcepcionValidacion;
import modelo.personas.TipoDocumento;
import org.jetbrains.annotations.NotNull;
import utils.GeneradorModel;

import java.util.Map;

public class RegistrarTecnicoCompletadoController implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        String nombre = context.formParam("nombre");
        String apellido = context.formParam("apellido");
        String tipoDoc = context.formParam("tipoDoc");
        String numeroDoc = context.formParam("numDoc");
        String cuil = context.formParam("nroCuil");
        String mail = context.formParam("email"); // Medio obligatorio
        String telefono = context.formParam("telefono"); // Medio opcional
        String direccion = context.formParam("direccion"); // Obligatorio
        String fechaNacimiento = context.formParam("fechaNacimiento"); // Obligatorio

        if (nombre.equals("") || apellido.equals("") || tipoDoc.equals("") ||
                numeroDoc.equals("") || cuil.equals("") || mail.equals("") ||
                direccion.equals("") || fechaNacimiento.equals(""))  {
            model.put("error", "Debe completar los campos obligatorios!");
            context.redirect("/registrarTecnico");
            return;
        }

        if ( !esNumerico(numeroDoc)  ||  (!telefono.equals("") && !esNumerico(telefono)) || !esNumerico(cuil) ) {
            model.put("error", "El número de documento o el teléfono o el cuil no son numéricos");
            //context.status(400);
            context.redirect("/registrarTecnico");
            return;
        }

        if ( !numeroDoc.matches("[0-9]{0,8}") )  {
            model.put("error", "El número de documento debe tener 8 dígitos");
            //context.status(400);
            context.redirect("/registrarTecnico");
            return;
        }

        if ( !cuil.matches("[0-9]{11}") )  {
            model.put("errorJuridico", "El número de CUIT debe tener 11 dígitos");
            //context.status(400);
            context.redirect("/registrarTecnico");
            return;
        }

        if ( !telefono.equals("")  &&  !telefono.matches("[0-9]{8,10}") )  {
            model.put("error", "El teléfono debe tener entre 8 y 10 dígitos");
            //context.status(400);
            context.redirect("/registrarTecnico");
            return;
        }

        TipoDocumento tipoDocumentoEnum;
        switch (tipoDoc){
            case "01" -> tipoDocumentoEnum = TipoDocumento.DNI;
            case "02" -> tipoDocumentoEnum = TipoDocumento.LC;
            case "03" -> tipoDocumentoEnum = TipoDocumento.LE;
            default -> tipoDocumentoEnum = TipoDocumento.DNI;
        }

        try {
            AuthServiceTecnico.registrarTecnico(nombre, apellido, tipoDocumentoEnum, numeroDoc, cuil, mail, telefono, direccion, fechaNacimiento);

        } catch (ExcepcionValidacion e) {
            model.put("error", e.getMessage());
            context.redirect("/registrarTecnico");
            return;
        }

        model.put("nombreUsuario", context.sessionAttribute("nombreUsuario"));
        context.render("templates/registroTecnicoFinal.mustache",model);

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
