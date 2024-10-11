package presentacion;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioColaboradores;

import java.util.HashMap;
import java.util.Map;

public class CuentaJuridicaCreadaController implements Handler {

    private RepositorioColaboradores repoColab;

    public CuentaJuridicaCreadaController(RepositorioColaboradores repoColab) {
        super();
        this.repoColab = repoColab;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = new HashMap<>();
        context.render("templates/elegirRegistroCuenta.mustache", model);

        String razonSocial = context.formParam("razon-social");
        String rubro = context.formParam("rubro");
        String email = context.formParam("email");
        String password = context.formParam("password");
        String terminos = context.formParam("terms");

        if (razonSocial == null || rubro == null || email == null || password == null || terminos == null) {
            model.put("error", "Debe completar todos los campos");
            context.status(400);
            context.redirect("/crearCuentaJuridica");
        }

        // hay que validad que no exista el mail en la base de datos
        //todo

        // crear la cuenta y enviar a
        // context.render("templates/cuentaCreada.mustache", model);
    }
}
