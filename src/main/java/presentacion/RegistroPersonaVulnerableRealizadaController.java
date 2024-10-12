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

        St

        context.redirect("/registroPersonaVulnerableFinal");
   }
}
