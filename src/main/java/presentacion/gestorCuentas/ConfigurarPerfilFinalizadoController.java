package presentacion.gestorCuentas;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.personas.MedioDeContacto;
import modelo.personas.Persona;
import modelo.personas.PersonaHumana;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioColaboradores;

import java.util.HashMap;
import java.util.Map;

public class ConfigurarPerfilFinalizadoController implements Handler {

    RepositorioColaboradores repoColaboradores;

    public ConfigurarPerfilFinalizadoController(RepositorioColaboradores repoColaboradores) {
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

        String nombre = context.formParam("nombre");
        String apellido = context.formParam("apellido");
        String direccion = context.formParam("direccion");
        String telefono = context.formParam("telefono");
        String email = context.formParam("email");

        Integer idPersona = context.sessionAttribute("idPersona");

        PersonaHumana personaModificada = repoColaboradores.traerPersonaPorIdFisica(idPersona);

        personaModificada.setNombre(nombre);
        personaModificada.setApellido(apellido);
        personaModificada.setDireccion(direccion);
        personaModificada.setTelefono(telefono);
        personaModificada.setEmail(email);

        repoColaboradores.actualizarPersona(personaModificada);

        context.redirect("/perfil");



    }
}
