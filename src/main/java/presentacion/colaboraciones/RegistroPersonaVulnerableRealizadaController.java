package presentacion.colaboraciones;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioPersonasVulnerables;

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
        //TODO: Con estos valores hay que crear una instancia de Persona Vulnerable vinculada a la Persona Física que la registró. Y guardarlo en el Repositorio.


        context.redirect("/registroPersonaVulnerableFinal");
   }
}
