package presentacion;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioPersonasVulnerables;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegistroPersonaVulnerableRealizadaController implements Handler{


    private RepositorioPersonasVulnerables repoPersonasVulnerables;

    public RegistroPersonaVulnerableRealizadaController(RepositorioPersonasVulnerables repoPersonas) {
        super();
        this.repoPersonasVulnerables = repoPersonas;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {

        String nombre = Objects.requireNonNull(context.formParam("nombre"));
        String fechaNacimientoStr = Objects.requireNonNull(context.formParam("fecha"));
        Long numeroDocumentoStr = Long.parseLong(Objects.requireNonNull(context.formParam("numero-documento")));
        String direccion = context.formParam("direccion");
        Long tarjeta = Long.parseLong(Objects.requireNonNull(context.formParam("tarjeta")));
        Integer cantidadMenoresStr = Integer.parseInt(Objects.requireNonNull(context.formParam("cantidad-menores")));

        context.redirect("/registroPersonaVulnerableFinal");
   }
}
