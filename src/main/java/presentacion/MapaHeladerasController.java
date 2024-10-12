package presentacion;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.elementos.Heladera;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioHeladeras;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapaHeladerasController implements Handler {

    private RepositorioHeladeras repoHeladeras;

    public MapaHeladerasController(RepositorioHeladeras repo) {
        super();
        this.repoHeladeras = repo;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = new HashMap<>();
        List<Heladera> heladerasAMostrar = this.repoHeladeras.obtenerHeladeras(5);
        model.put("listaHeladeras", heladerasAMostrar);

        context.render("templates/mapaHeladeras.mustache", model);
    }

}
