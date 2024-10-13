package presentacion.gestorCuentas;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioColaboradores;

import java.util.HashMap;
import java.util.Map;

public class CuentaFisicaCreadaController implements Handler {

    private RepositorioColaboradores repoColaboradores;

    public CuentaFisicaCreadaController(RepositorioColaboradores repositorioColaboradores) {
        super();
        this.repoColaboradores = repositorioColaboradores;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = new HashMap<>();
        context.render("templates/elegirRegistroCuenta.mustache", model);

        String nombre = context.formParam("nombre");
        String apellido = context.formParam("apellido");
        String email = context.formParam("email");
        String password = context.formParam("password");
        String terminos = context.formParam("terms");

        if (nombre == null || apellido == null || email == null || password == null || terminos == null) {
            model.put("error", "Debe completar todos los campos");
            context.render("templates/crearCuentaFisica.mustache", model);
            context.status(400);
            context.redirect("/crearCuentaFisica");
        }

        // hay que validad que no exista el mail en la base de datos
        //todo

        // crear la cuenta y enviar a
        context.redirect("/cuentaCreada");
    }
}
