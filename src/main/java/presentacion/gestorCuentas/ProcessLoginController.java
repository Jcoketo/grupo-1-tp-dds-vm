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
import utils.GeneradorModel;

public class ProcessLoginController implements Handler {

    private RepositorioColaboradores repoColab = RepositorioColaboradores.getInstancia();
    private RepositorioUsuarios repoUsuarios = RepositorioUsuarios.getInstancia();

    public ProcessLoginController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);
        String email = context.formParam("email");
        String password = context.formParam("password");

        try {
            AuthServiceUsuario.autenticarUsuario(email, password);
        } catch (ExcepcionValidacion e) {
            model.put("errorLogin", "El mail o la contraseña son incorrectos");
            context.sessionAttribute("errorLogin", "El mail o la contraseña son incorrectos");
            context.redirect("/login");
            return;
        }

        context.sessionAttribute("logueado", true);

        TipoPersona tipoPer = repoColab.devolverTipoPersona(email);
        context.sessionAttribute("tipoPersona", tipoPer);


        Integer idPersona = repoColab.devolverIdPersona(email);
        context.sessionAttribute("idPersona", idPersona);

        Usuario usuario = repoUsuarios.traerUsuario(email);
        context.sessionAttribute("rolUsuario", usuario.getRol());
        context.sessionAttribute("nombreUsuario", usuario.getUsername());

        model.put("nombreUsuario", usuario.getUsername());

        if ( usuario.getUsername() == null ||  usuario.getUsername().equals("") ) {
            context.sessionAttribute("mail", email);
            context.redirect("/validarDatos");
            return;
        }
        if (usuario.getRol() == Roles.ADMIN){
            context.redirect("/inicioADMIN");
            return;
        }

        context.redirect("/inicio");

    }


}
