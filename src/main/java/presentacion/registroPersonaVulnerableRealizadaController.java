package presentacion;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.personas.Documento;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioPersonasVulnerables;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class registroPersonaVulnerableRealizadaController implements Handler{


    private RepositorioPersonasVulnerables repoPersonas;

    public registroPersonaVulnerableRealizadaController(RepositorioPersonasVulnerables repoPersonas) {
        super();
        this.repoPersonas = repoPersonas;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = new HashMap<>();

        String nombre = context.pathParam("nombre");
        LocalDateTime fechaNacimiento = LocalDateTime.now();
        String documento = context.pathParam("documento");
        String domicilio = context.pathParam("domicilio");
        Integer menoresACargo = Integer.parseInt(context.pathParam("menoresACargo"));
        Integer nroTarjeta = Integer.parseInt(context.pathParam("nroTarjeta"));

        context.redirect("/registroPersonaVulnerableFinal");
    }

}
