package presentacion.heladera;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioHeladeras;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HeladeraAgregadaController implements Handler {
    private RepositorioHeladeras repoHeladeras;

    public HeladeraAgregadaController(RepositorioHeladeras repoHeladeras) {
        super();
        this.repoHeladeras = repoHeladeras;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = new HashMap<>();
        String nombre = context.formParam("nombre");
        String fechaInicio = context.formParam("fechaInicio"); // falta parsear a LocalDate
        Integer capacidad = Integer.parseInt(Objects.requireNonNull(context.formParam("capacidad")));
        String activa = context.formParam("activa");
        String direccion = context.formParam("ubicacion");

    }
}
