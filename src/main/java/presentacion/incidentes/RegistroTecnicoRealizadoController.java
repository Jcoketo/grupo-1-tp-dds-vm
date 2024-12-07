package presentacion.incidentes;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.authService.AuthServiceTecnico;
import modelo.elementos.Areas;
import modelo.excepciones.ExcepcionValidacion;
import modelo.personas.TipoDocumento;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositoriosTecnicos;

import java.util.HashMap;
import java.util.Map;

public class RegistroTecnicoRealizadoController implements Handler {

    public RepositoriosTecnicos repoTecnicos;

    public RegistroTecnicoRealizadoController (RepositoriosTecnicos repoTecnicos){
        super();
        this.repoTecnicos = repoTecnicos;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = context.sessionAttribute("model");
        if (model == null) {
            model = new HashMap<>();
            context.sessionAttribute("model", model);
        }

        String nombre = context.formParam("nombre");
        String apellido = context.formParam("apellido");
        String tipoDoc = context.formParam("tipoDoc");
        String numeroDoc = context.formParam("numDoc");
        String cuil = context.formParam("cuil");
        String mail = context.formParam("mail"); // Medio obligatorio
        String telefono = context.formParam("telefono");// Medio opcional
        Integer areaCobertura = Integer.valueOf(context.formParam("area"));

        if (nombre.equals("") || apellido.equals("") || tipoDoc.equals("") ||
            numeroDoc.equals("") || cuil.equals("") || mail.equals("") ||
            areaCobertura.equals("") )  {
            model.put("error", "Debe completar los campos obligatorios!");
            //context.status(400);
            context.redirect("/registrarTecnico");
            return;
        }

        if ( !esNumerico(numeroDoc)  ||  (!telefono.equals("") && !esNumerico(telefono)) ) {
            model.put("error", "El número de documento o el teléfono no son numéricos");
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
            default -> throw new ExcepcionValidacion("Unexpected value: " + tipoDoc);
        }

        Areas area;
        switch (areaCobertura) {
            case 1:
                area = Areas.CABALLITO;
                break;
            case 2:
                area = Areas.PALERMO;
                break;
            case 3:
                area = Areas.RECOLETA;
                break;
            default:
                area = Areas.BELGRANO;
                break;
        } // TODO: terminar de completar este switch con ChatGPT!

        try {
            AuthServiceTecnico.registrarTecnico(nombre, apellido, tipoDocumentoEnum, numeroDoc, cuil, mail, telefono, area);

        } catch (ExcepcionValidacion e) {
            model.put("error", e.getMessage());
            context.redirect("/registrarTecnico");
            return;
        }

        context.redirect("/tecnicoRegistrado");
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
