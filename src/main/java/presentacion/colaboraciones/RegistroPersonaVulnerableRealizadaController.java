package presentacion.colaboraciones;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.authService.AuthServicePersonaVulnerable;
import modelo.excepciones.ExcepcionValidacion;
import modelo.personas.TipoDocumento;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioPersonasVulnerables;
import persistencia.RepositorioTarjetas;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegistroPersonaVulnerableRealizadaController implements Handler{

    public RegistroPersonaVulnerableRealizadaController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = context.sessionAttribute("model");
        if (model == null) {
            model = new HashMap<>();
            context.sessionAttribute("model", model);
        }

        String nombre = Objects.requireNonNull(context.formParam("nombre"));
        Integer tieneDoc = Integer.parseInt(Objects.requireNonNull(context.formParam("tieneDoc")));
        String tipoDoc = context.formParam("tipoDoc");
        String numeroDocumento = context.formParam("numDoc");
        Integer tieneDom = Integer.parseInt((context.formParam("tieneDom")));
        String domicilio = context.formParam("domicilio");
        String nroTarjeta = context.formParam("numTarjeta");
        Integer tieneMenores = Integer.parseInt((context.formParam("tieneMenores")));
        Integer cantidadMenores = Integer.parseInt((context.formParam("cantidadMenores")));

        if ( nombre.equals("") || ( numeroDocumento.equals("") && tieneDoc.equals(1) ) || ( tieneDom.equals(1) && domicilio.equals("")) ){
            model.put("errorRegistroVulnerable", "Debe completar los campos obligatorios");
            context.redirect("/registroPersonaVulnerable");
            return;
        }

        if (  ( !esNumerico(numeroDocumento) && tieneDoc.equals(1) ) || !esNumerico(nroTarjeta) )  {
            model.put("errorRegistroVulnerable", "El número de documento o el numero de tarjeta no son numéricos");
            context.redirect("/registroPersonaVulnerable");
            return;
        }

        if ( !numeroDocumento.matches("[0-9]{0,8}") && tieneDoc.equals(1) )  {
            model.put("errorRegistroVulnerable", "El número de documento debe tener 8 dígitos");
            context.redirect("/registroPersonaVulnerable");
            return;
        }

        if (tieneMenores.equals(1) && cantidadMenores.equals(0)){
            model.put("errorRegistroVulnerable", "Debe elegir la cantidad de menores a cargo distinta de 0.");
            context.redirect("/registroPersonaVulnerable");
            return;
        }

        if ( !nroTarjeta.matches("[0-9]{11}"))  {
            model.put("errorRegistroVulnerable", "El número de la tarjeta debe tener 11 dígitos");
            context.redirect("/registroPersonaVulnerable");
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
            Integer idPersona = context.sessionAttribute("idPersona");
            AuthServicePersonaVulnerable.procesarAltaPersonaVulnerable(idPersona, nombre, tipoDocumentoEnum, numeroDocumento, domicilio, nroTarjeta, cantidadMenores);
        } catch (ExcepcionValidacion e) {
            model.put("errorRegistroVulnerable", e.getMessage());
            context.redirect("/registroPersonaVulnerable");
            return;
        }

        context.redirect("/registroPersonaVulnerableFinal");
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
