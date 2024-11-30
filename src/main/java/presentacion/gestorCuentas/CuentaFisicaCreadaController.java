package presentacion.gestorCuentas;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.autenticacion.AuthService;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioColaboradores;
import persistencia.RepositorioUsuarios;

import java.util.HashMap;
import java.util.Map;

public class CuentaFisicaCreadaController implements Handler {

    private RepositorioColaboradores repoColaboradores;
    private RepositorioUsuarios repoUsuarios;

    public CuentaFisicaCreadaController(RepositorioColaboradores repositorioColaboradores, RepositorioUsuarios repositorioUsuarios) {
        super();
        this.repoColaboradores = repositorioColaboradores;
        this.repoUsuarios = repositorioUsuarios;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = new HashMap<>();
        //context.render("templates/elegirRegistroCuenta.mustache", model);

        String nombre = context.formParam("nombre");
        String apellido = context.formParam("apellido");
        String username = context.formParam("username");
        String email = context.formParam("email");
        String password = context.formParam("password");
        String terminos = context.formParam("terms");


        // esto creo que esta mal, nunca va a ser null, deberiamos chequear que el string no sea == ' '
        if (nombre == null || apellido == null || email == null || password == null || terminos == null) {
            model.put("error", "Debe completar todos los campos");
            //context.render("templates/crearCuentaFisica.mustache", model);
            context.status(400);
            context.redirect("/crearCuentaFisica"); // <-- este hace el render de la vista
            // se puede agregar que se pase x param los que ya ingreso
        }

        // creamos el usuario

        repoUsuarios.registrarUsuario(email, username, password);

        // hay que validad que no exista el mail en la base de datos
        //todo

        // crear la cuenta y enviar a
        context.redirect("/cuentaCreada");
    }
}
