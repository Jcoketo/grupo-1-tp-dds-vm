package presentacion.gestorCuentas;

import java.util.HashMap;
import java.util.Map;

import modelo.excepciones.ExcepcionValidacion;
import org.jetbrains.annotations.NotNull;

import accessManagment.Roles;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.personas.TipoPersona;
import persistencia.RepositorioColaboradores;
import modelo.authService.AuthServiceUsuario;

public class ProcessLoginController implements Handler {

    private RepositorioColaboradores repoColab;
    private AuthServiceUsuario authServiceUsuario;

    public ProcessLoginController(RepositorioColaboradores repoColab) {
        super();
        this.repoColab = repoColab;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        String email = context.formParam("email");
        String password = context.formParam("password");
        boolean valido = false;

        Map<String, Object> model = new HashMap<>();

        try {
            valido = AuthServiceUsuario.autenticarUsuario(email, password);
        } catch (ExcepcionValidacion e) {
            model.put("error", "El mail o la contraseña son incorrectos");
            context.redirect("/login");
        }

        if (valido) {
            context.sessionAttribute("logueado", true);

            TipoPersona tipoPer = obtenerTipoUsuario(email);
            context.sessionAttribute("tipoPersona", tipoPer);

            Roles userRole = obtenerRolUsuario(email);
            context.sessionAttribute("rolUsuario", userRole);

            context.redirect("/inicio");

        }
    }

    public Roles obtenerRolUsuario(String email) {
        return repoColab.devolverRol(email);
    }

    public TipoPersona obtenerTipoUsuario(String email) {
        return repoColab.devolverTipoPersona(email);
    }

}
