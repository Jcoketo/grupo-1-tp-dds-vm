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

    private RepositorioHeladeras repositorioHeladeras;

    public MapaHeladerasController(RepositorioHeladeras repo) {
        super();
        this.repositorioHeladeras = repo;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        // Obtén las heladeras del repositorio
        List<Heladera> heladeras = this.repositorioHeladeras.obtenerHeladeras(5);

        // Devuelve las heladeras en formato JSON
        context.json(heladeras);
    }


}
