package utils;

import io.javalin.http.Context;
import modelo.personas.TipoPersona;

import java.util.HashMap;
import java.util.Map;

public class GeneradorModel {
    public static Map<String, Object> getModel(Context context) {
        Map<String, Object> model = new HashMap<>();

        model.put("nombreUsuario", context.sessionAttribute("nombreUsuario"));
        model.put("logueado", context.sessionAttribute("logueado"));
        model.put("idPersona", context.sessionAttribute("idPersona"));
        model.put("tipoPersona", context.sessionAttribute("tipoPersona"));
        model.put("rolUsuario", context.sessionAttribute("rolUsuario"));
        model.put("validado", context.sessionAttribute("validado"));
        model.put("esAdmin", context.sessionAttribute("esAdmin"));
        model.put("esPersonaJuridica", context.sessionAttribute("tipoPersona") == TipoPersona.PJ);

        return model;
    }
}
