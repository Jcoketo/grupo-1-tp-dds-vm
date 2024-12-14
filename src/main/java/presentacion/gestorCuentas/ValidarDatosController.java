package presentacion.gestorCuentas;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.personas.PersonaHumana;
import modelo.personas.Tecnico;
import modelo.validador.Usuario;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioColaboradores;
import persistencia.RepositorioUsuarios;
import persistencia.RepositoriosTecnicos;
import utils.GeneradorModel;

import java.util.Map;

public class ValidarDatosController implements Handler {

    private static RepositorioColaboradores repoColab = RepositorioColaboradores.getInstancia();
    private static RepositorioUsuarios repoUsuarios = RepositorioUsuarios.getInstancia();
    private static RepositoriosTecnicos repoTecnicos = RepositoriosTecnicos.getInstancia();

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        Integer idPersona = context.sessionAttribute("idPersona");
        String mail = context.sessionAttribute("mail");

        PersonaHumana persona = repoColab.buscarPersonaPorId(idPersona);
        if (persona == null) {
            model.put("errorValidarDatos", "Error en el sistema, notifique a sistemas!");
            context.redirect("/inicio"); //TODO falta enviar un mensaje de error al front
            return;
        }

        Usuario usuario = repoUsuarios.traerUsuario(mail);

        model.put("nombre", persona.getNombre());
        model.put("apellido", persona.getApellido());
        model.put("tipoDoc", persona.getDocumento().getTipoDoc());
        model.put("numeroDoc", persona.getDocumento().getNumeroDoc());
        model.put("mail", mail);
        model.put("telefono", persona.getTelefono());
        model.put("direccion", persona.getDireccion());
        model.put("fechaNacimiento", persona.getFechaNacimiento());
        model.put("username", usuario.getUsername());

        Tecnico tecnico = repoTecnicos.buscarTecnicoXIdPersona(idPersona);
        if ( tecnico != null) { // es tecnico
            model.put("esTecnico", true);
            model.put("cuil", tecnico.getNroCUIL());
        }
        context.render("templates/validarDatos.mustache", model);
    }
}
