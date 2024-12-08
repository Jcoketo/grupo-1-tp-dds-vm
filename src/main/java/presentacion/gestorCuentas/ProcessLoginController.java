package presentacion.gestorCuentas;

import java.util.HashMap;
import java.util.Map;

import modelo.excepciones.ExcepcionValidacion;
import modelo.validador.Usuario;
import org.jetbrains.annotations.NotNull;

import accessManagment.Roles;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.personas.TipoPersona;
import persistencia.RepositorioColaboradores;
import persistencia.RepositorioUsuarios;
import modelo.authService.AuthServiceUsuario;

public class ProcessLoginController implements Handler {

    private RepositorioColaboradores repoColab;
    private RepositorioUsuarios repoUsuarios;
    private AuthServiceUsuario authServiceUsuario;

    public ProcessLoginController(RepositorioColaboradores repoColab, RepositorioUsuarios repoUsuarios) {
        super();
        this.repoColab = repoColab;
        this.repoUsuarios = repoUsuarios;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        String email = context.formParam("email");
        String password = context.formParam("password");
        Map<String, Object> model = context.sessionAttribute("model");
        if (model == null) {
            model = new HashMap<>();
            context.sessionAttribute("model", model);
        }

        try {
            AuthServiceUsuario.autenticarUsuario(email, password);
        } catch (ExcepcionValidacion e) {
            model.put("errorLogin", "El mail o la contraseña son incorrectos");
            context.sessionAttribute("model", model);
            context.redirect("/login");
            return;
        }

        context.sessionAttribute("logueado", true);

        TipoPersona tipoPer = repoColab.devolverTipoPersona(email);
        context.sessionAttribute("tipoPersona", tipoPer);

        Integer idPersona = repoColab.devolverIdUsuario(email);
        context.sessionAttribute("idPersona", idPersona);

        Usuario usuario = repoUsuarios.traerUsuario(email);
        context.sessionAttribute("rolUsuario", usuario.getRol());
        context.sessionAttribute("nombreUsuario", usuario.getUsername());

        model.put("nombreUsuario", usuario.getUsername());

        if (usuario.getRol() == Roles.ADMIN){
            context.redirect("/inicioADMIN");
            return;
        }

        context.redirect("/inicio");

    }


}
