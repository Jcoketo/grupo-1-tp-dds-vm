package presentacion.colaboraciones;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioHeladeras;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class DonarViandaRealizadaController implements Handler {

    private RepositorioHeladeras repoHeladeras;

    public DonarViandaRealizadaController(RepositorioHeladeras repoHeladeras) {
        super();
        this.repoHeladeras = repoHeladeras;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = new HashMap<>();
        String Heladera = context.formParam("heladera");
        String comida = context.formParam("vianda1");
        String fecha = context.formParam("fechaCaducidad1");
        Double pesoVianda = Double.parseDouble(context.formParam("pesoVianda1"));
        Double caloridas = Double.parseDouble(context.formParam("caloriasVianda1"));
        LocalDateTime fechaDonacion = LocalDateTime.now();
        // TODO: Crear la instancia de la vianda y guardarla en la instancia de la heladera seleccionada.
        //TODO VERIFICAR SI VIENE 1 O 2 VIANDAS

        context.redirect("/graciasPorDonar");
    }
}
