package presentacion;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioColaboradores;

import java.util.HashMap;
import java.util.Map;

public class ProcessLoginController implements Handler {

    private RepositorioColaboradores repoColab;

    public ProcessLoginController(RepositorioColaboradores repoColab) {
        super();
        this.repoColab = repoColab;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        String email = context.formParam("email");
        String password = context.formParam("password");

        Map<String, Object> model = new HashMap<>();
        if (authenticateUser(email, password)) {
            context.sessionAttribute("isLogged", true);
            context.redirect("/inicio-conLog");
        } else {
            model.put("error", "Invalid email or password");
            context.render("templates/login.mustache", model);
        }
    }

    private boolean authenticateUser(String email, String password) {
        // Lógica de autenticación (ejemplo simple)
        // ir a la BD
        return "usuario@dominio.com".equals(email) && "password123".equals(password);
    }
}
