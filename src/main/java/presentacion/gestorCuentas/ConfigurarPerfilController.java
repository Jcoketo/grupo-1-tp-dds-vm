package presentacion.gestorCuentas;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.personas.Colaborador;
import modelo.personas.PersonaHumana;
import modelo.personas.PersonaJuridica;
import modelo.personas.TipoPersona;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioColaboradores;

import java.util.HashMap;
import java.util.Map;

public class ConfigurarPerfilController implements Handler {

    RepositorioColaboradores repoColaboradores;

    public ConfigurarPerfilController(RepositorioColaboradores repoColaboradores) {
        super();
        this.repoColaboradores = repoColaboradores;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = context.sessionAttribute("model");
        if (model == null) {
            model = new HashMap<>();
            context.sessionAttribute("model", model);
        }
        Integer idPersona = context.sessionAttribute("idPersona");
        TipoPersona tipoPer = context.sessionAttribute("tipoPersona");
        model.put("nombreUsuario", context.sessionAttribute("nombreUsuario"));
        Colaborador colab = repoColaboradores.buscarColaboradorXIdPersona(idPersona);

        if (tipoPer == TipoPersona.PH) {
            PersonaHumana persona = repoColaboradores.traerPersonaPorIdFisica(idPersona);
            String nombreApellido = persona.getNombre() + " " + persona.getApellido();
            model.put("nombre", nombreApellido);
            model.put("email", persona.getEmail());
            model.put("telefono", persona.getTelefono());
            model.put("direccion", persona.getDireccion());
            model.put("puntos", colab.getPuntaje());
        }
        if (tipoPer == TipoPersona.PJ) {
            PersonaJuridica persona = repoColaboradores.traerPersonaPorIdJuridica(idPersona);
            model.put("nombre", persona.getRazonSocial());
            model.put("email", persona.getEmail());
            model.put("telefono", persona.getTelefono());
            model.put("direccion", persona.getDireccion());
            model.put("puntos", colab.getPuntaje());
        }

        context.render("templates/configurarPerfil.mustache", model);
    }
}
