package presentacion.ofertas;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import persistencia.RepositorioColaboradores;
import persistencia.RepositorioOfertas;

import java.util.HashMap;
import java.util.Map;

public class CanjearPuntosFinalizadoController  implements Handler {

    RepositorioColaboradores repoColaboradores;
    RepositorioOfertas repoOfertas;

    public CanjearPuntosFinalizadoController(RepositorioColaboradores repoColaboradores, RepositorioOfertas repositorioOfertas) {
        super();
        this.repoColaboradores = repoColaboradores;
        this.repoOfertas = repositorioOfertas;
    }

    @Override
    public void handle(Context context) throws Exception {
        Map<String, Object> model = context.sessionAttribute("model");
        if (model == null) {
            model = new HashMap<>();
            context.sessionAttribute("model", model);
        }


    }

}
