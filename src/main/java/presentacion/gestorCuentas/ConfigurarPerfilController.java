package presentacion.gestorCuentas;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.personas.Colaborador;
import modelo.personas.PersonaHumana;
import modelo.personas.PersonaJuridica;
import modelo.personas.TipoPersona;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioColaboradores;
import utils.GeneradorModel;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ConfigurarPerfilController implements Handler {

    RepositorioColaboradores repoColaboradores = RepositorioColaboradores.getInstancia();

    public ConfigurarPerfilController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        NotificacionCambio notificacionCambio = context.sessionAttribute("notificacionCambio");
        if(notificacionCambio != null){
            model.put("notificacionCambio", notificacionCambio);
        }
        context.consumeSessionAttribute("notificacionCambio");

        Integer idPersona = context.sessionAttribute("idPersona");
        TipoPersona tipoPer = context.sessionAttribute("tipoPersona");
        model.put("nombreUsuario", context.sessionAttribute("nombreUsuario"));
        model.put("tipoPer", tipoPer);

        if (tipoPer == TipoPersona.PH) {
            PersonaHumana persona = repoColaboradores.traerPersonaPorIdFisica(idPersona);
            model.put("esPersonaHumana", true);
            model.put("nombre", persona.getNombre());
            model.put("apellido", persona.getApellido());
            model.put("fechaNacimiento", persona.getFechaNacimiento());
            model.put("direccion", persona.getDireccion());
            model.put("telefono", persona.getTelefono());
            model.put("email", persona.getEmail());
        }
        if (tipoPer == TipoPersona.PJ) {
            PersonaJuridica persona = repoColaboradores.traerPersonaPorIdJuridica(idPersona);
            model.put("esPersonaJuridica", true);
            model.put("nombre", persona.getRazonSocial());
            model.put("cuit", persona.getCUIT());
            model.put("tipoJuridico", persona.getTipoJuridico());
            model.put("rubro", persona.getRubro());
            model.put("direccion", persona.getDireccion());
            model.put("telefono", persona.getTelefono());
            model.put("email", persona.getEmail());
        }

        context.render("templates/configurarPerfil.mustache", model);
    }
}
