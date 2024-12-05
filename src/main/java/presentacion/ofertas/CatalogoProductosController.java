package presentacion.ofertas;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.colaboracion.Oferta;
import modelo.colaboracion.TipoOferta;
import modelo.personas.Colaborador;
import modelo.personas.TipoPersona;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioOfertas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.HashMap;
import java.util.Map;

public class CatalogoProductosController implements Handler {

    private RepositorioOfertas repoOfertas;

    public CatalogoProductosController(RepositorioOfertas repoOfertas) {
        super();
        this.repoOfertas = repoOfertas;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {

        Map<String, Object> model = context.sessionAttribute("model");
        if (model == null) {
            model = new HashMap<>();
            context.sessionAttribute("model", model);
        }


        String opcion = context.queryParam("filter");

        List<Oferta> ofertas;

        switch (opcion) {
            case "1":
                ofertas = repoOfertas.conocerOfertasDisponibles().stream()
                        .filter(oferta -> oferta.getTipoOferta().equals(TipoOferta.SERVICIO))
                        .toList();
                break;
            case "2":
                ofertas = repoOfertas.conocerOfertasDisponibles().stream()
                        .filter(oferta -> oferta.getTipoOferta().equals(TipoOferta.PRODUCTO))
                        .toList();
                break;
            default:
                ofertas = repoOfertas.conocerOfertasDisponibles();
                break;
        }

        model.put("ofertas", ofertas);

        context.render("templates/catalogoProductos.mustache", model);
    }

}
