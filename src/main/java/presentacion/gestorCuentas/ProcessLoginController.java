package presentacion.gestorCuentas;

import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import accessManagment.Roles;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.personas.TipoPersona;
import persistencia.RepositorioColaboradores;
import modelo.autenticacion.AuthService;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioUsuarios;

public class ProcessLoginController implements Handler {

    private RepositorioUsuarios repoUsuarios;
    private RepositorioColaboradores repoColab;
    //private AuthService authService; ??? Qué hace esto COCO

    public ProcessLoginController(RepositorioUsuarios repoUsuarios, RepositorioColaboradores repoColaboradores) {
        super();
        this.repoUsuarios = repoUsuarios;
        this.repoColab = repoColaboradores;
        //this.authService = authService; ??? Qué hace esto COCO
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        String email = context.formParam("email");
        String password = context.formParam("password");

        Map<String, Object> model = new HashMap<>();
        if (repoUsuarios.autenticarUsuario(email, password)) {
            context.sessionAttribute("logueado", true);

            TipoPersona tipoPer = obtenerTipoUsuario(email);
            context.sessionAttribute("tipoPersona", tipoPer);

            Roles userRole = obtenerRolUsuario(email);
            context.sessionAttribute("rolUsuario", userRole);

            context.redirect("/inicio");

        } else {
            model.put("error", "Invalid email or password");
            context.render("templates/login.mustache", model);
        }
    }

    public Roles obtenerRolUsuario(String email) {
        return repoColab.devolverRol(email);
    }

    public TipoPersona obtenerTipoUsuario(String email) {
        return repoColab.devolverTipoPersona(email);
    }


    /* ---------  VIEJO ---------
     * Método de autenticación de usuario
     *
     * @param email    email del usuario
     * @param password contraseña del usuario
     * @return true si el usuario es autenticado, false en caso contrario

    private boolean authenticateUser(String email, String password) {
        // Lógica de autenticación (ejemplo simple)
        // ir a la BD
        return "usuario@dominio.com".equals(email) && "password123".equals(password);
    }*/
}
