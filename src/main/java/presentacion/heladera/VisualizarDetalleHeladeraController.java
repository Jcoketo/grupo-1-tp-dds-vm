package presentacion.heladera;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashMap;
import java.util.Map;

public class VisualizarDetalleHeladeraController implements Handler{
    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = context.sessionAttribute("model");
        if (model == null) {
            model = new HashMap<>();
            context.sessionAttribute("model", model);
        }
        Boolean estaLogueado = context.sessionAttribute("logueado");
        //if( estaLogueado == null ){ estaLogueado = false; }
        model.put("logueado", estaLogueado != null && estaLogueado);
        model.put("nombreUsuario", context.sessionAttribute("nombreUsuario"));

        String id = context.queryParam("heladeraId");
        String nombre = context.queryParam("nombre");
        String direccion = context.queryParam("direccion");
        Double latitud = Double.parseDouble(context.queryParam("lat"));
        Double longitud = Double.parseDouble(context.queryParam("long"));
        String fecha = context.queryParam("fecha");

        String estado = context.queryParam("estado");
        Integer disponibilidad = Integer.parseInt(context.queryParam("disponibilidad"));

        model.put("heladeraId",id);
        model.put("nombre",nombre);
        model.put("direccion",direccion);
        model.put("latitud",latitud);
        model.put("longitud",longitud);

        model.put("fecha",fecha);
        model.put("estado",estado);
        model.put("disponibilidad",disponibilidad);



        context.render("templates/visualizarDetalleHeladera.mustache", model);

    }
}
