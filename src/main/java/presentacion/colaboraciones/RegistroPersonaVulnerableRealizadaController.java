package presentacion.colaboraciones;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.authService.AuthServicePersonaVulnerable;
import modelo.excepciones.ExcepcionValidacion;
import modelo.personas.TipoDocumento;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioPersonasVulnerables;
import persistencia.RepositorioTarjetas;
import utils.GeneradorModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegistroPersonaVulnerableRealizadaController implements Handler{

    public RegistroPersonaVulnerableRealizadaController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        String nombre = context.formParam("nombre");
        String fechaNac = context.formParam("fecha");
        String tieneDoc = context.formParam("tieneDoc");
        String tipoDoc = context.formParam("tipoDoc");
        String numeroDocumento = context.formParam("numDoc");
        String tieneDom = context.formParam("tieneDom");
        String domicilio = context.formParam("domicilio");
        String nroTarjeta = context.formParam("numTarjeta");
        String tieneMenores = context.formParam("tieneMenores");
        Integer cantidadMenores = Integer.parseInt((context.formParam("cantidadMenores")));

        if (tieneDoc == null){
            tieneDoc = "0";
        }
        if ( tieneDom == null){
            tieneDom = "0";
        }
        if ( fechaNac == null || fechaNac.equals("") ){
            context.sessionAttribute("errorRegistroVulnerable", "Debe completar la fecha de nacimiento!");
            context.redirect("/registroPersonaVulnerable");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaNacimiento = LocalDate.parse(fechaNac, formatter);

        if ( fechaNacimiento.isAfter(LocalDate.now()) ){
            context.sessionAttribute("errorRegistroVulnerable", "La fecha de nacimiento no puede ser posterior al dia de hoy.");
            context.redirect("/registroPersonaVulnerable");
            return;
        }

        if ( nombre.equals("") || ( numeroDocumento.equals("") && tieneDoc.equals("1") ) || ( tieneDom.equals("1") && domicilio.equals("")) ){
            context.sessionAttribute("errorRegistroVulnerable", "Debe completar todos los campos obligatorios.");
            context.redirect("/registroPersonaVulnerable");
            return;
        }

        if (  ( !esNumerico(numeroDocumento) && tieneDoc.equals("1") ) || !esNumerico(nroTarjeta) )  {
            context.sessionAttribute("errorRegistroVulnerable", "El número de documento o el numero de tarjeta no son numéricos");
            context.redirect("/registroPersonaVulnerable");
            return;
        }

        if ( !numeroDocumento.matches("[0-9]{0,8}") && tieneDoc.equals("1") )  {
            context.sessionAttribute("errorRegistroVulnerable", "El número de documento debe tener 8 dígitos");
            context.redirect("/registroPersonaVulnerable");
            return;
        }

        if (tieneMenores.equals("1") && cantidadMenores == 0 ){
            context.sessionAttribute("errorRegistroVulnerable", "Debe elegir la cantidad de menores a cargo.");
            context.redirect("/registroPersonaVulnerable");
            return;
        }

        if ( !nroTarjeta.matches("[0-9]{11}"))  {
            context.sessionAttribute("errorRegistroVulnerable", "El número de la tarjeta debe tener 11 dígitos");
            context.redirect("/registroPersonaVulnerable");
            return;
        }

        TipoDocumento tipoDocumentoEnum;
        switch (tipoDoc){
            case "01" -> tipoDocumentoEnum = TipoDocumento.DNI;
            case "02" -> tipoDocumentoEnum = TipoDocumento.LC;
            case "03" -> tipoDocumentoEnum = TipoDocumento.LE;
            default -> tipoDocumentoEnum = null;
       }

        try {
            Integer idPersona = context.sessionAttribute("idPersona");
            AuthServicePersonaVulnerable.procesarAltaPersonaVulnerable(idPersona, nombre, tipoDocumentoEnum, numeroDocumento,
                                                                        domicilio, nroTarjeta, cantidadMenores, fechaNacimiento);
        } catch (ExcepcionValidacion e) {
            context.sessionAttribute("errorRegistroVulnerable", e.getMessage());
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
