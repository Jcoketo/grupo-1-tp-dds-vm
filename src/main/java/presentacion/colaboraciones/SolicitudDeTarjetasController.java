package presentacion.colaboraciones;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.authService.AuthServiceColaboracion;
import modelo.authService.AuthServiceColaborador;
import modelo.excepciones.ExcepcionValidacion;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioColaboradores;

import java.util.HashMap;
import java.util.Map;

public class SolicitudDeTarjetasController implements Handler {

    private RepositorioColaboradores repoColab;

    public SolicitudDeTarjetasController(RepositorioColaboradores repo) {
        super();
        this.repoColab = repo;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = context.sessionAttribute("model");
        if (model == null) {
            model = new HashMap<>();
            context.sessionAttribute("model", model);
        }

        Integer IdPersona = context.sessionAttribute("idPersona");


        try {
            AuthServiceColaboracion.registrarPersonasVulnerables(IdPersona);

        } catch (ExcepcionValidacion e) {
            e.getMessage();
            context.redirect("/errorRegistro");
        }

        context.redirect("/elegirDonacion");
    }
}
