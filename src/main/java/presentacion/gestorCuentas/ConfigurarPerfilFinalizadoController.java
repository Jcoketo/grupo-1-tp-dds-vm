package presentacion.gestorCuentas;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioColaboradores;

import java.util.HashMap;
import java.util.Map;

public class ConfigurarPerfilFinalizadoController implements Handler {

    RepositorioColaboradores repoColaboradores;

    public ConfigurarPerfilFinalizadoController(RepositorioColaboradores repoColaboradores) {
        super();
        this.repoColaboradores = repoColaboradores;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = context.sessionAttribute("model");
        if (model == null) {
            model = new HashMap<>();
            context.sessionAttribute("model", model);
        }
        Integer idPersona = context.sessionAttribute("idPersona");
    }
}
