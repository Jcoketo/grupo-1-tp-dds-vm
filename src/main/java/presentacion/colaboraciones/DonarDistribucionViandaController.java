package presentacion.colaboraciones;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.Getter;
import lombok.Setter;
import modelo.elementos.Heladera;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioHeladeras;
import utils.GeneradorModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DonarDistribucionViandaController implements Handler {

    public DonarDistribucionViandaController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        String idHeladeraOrigen = context.queryParam("idHeladeraOrigen");
        String heladera1 = context.queryParam("heladera1");
        String direccion1 = context.queryParam("direccion1");
        String estado1 = context.queryParam("estado1");
        String disponibilidad1 = context.queryParam("disponibilidad1");

        String idHeladeraDestino = context.queryParam("idHeladeraDestino");
        String heladera2 = context.queryParam("heladera2");
        String direccion2 = context.queryParam("direccion2");
        String estado2 = context.queryParam("estado2");
        String disponibilidad2 = context.queryParam("disponibilidad2");

        if(!esNatural(context.sessionAttribute("id_heladera_origen"))){
            context.sessionAttribute("id_heladera_origen",idHeladeraOrigen);
        }

        model.put("heladera1", heladera1);
        model.put("direccion1", direccion1);
        model.put("estado1", estado1);
        model.put("disponibilidad1", disponibilidad1);

        if(!esNatural(context.sessionAttribute("id_heladera_destino"))){
            context.sessionAttribute("id_heladera_destino",idHeladeraDestino);
        }

        model.put("heladera2", heladera2);
        model.put("direccion2", direccion2);
        model.put("estado2", estado2);
        model.put("disponibilidad2", disponibilidad2);

        context.render("templates/donarDistribuirViandas.mustache", model);

    }

    public boolean esNatural(String numero){
        if (numero == null ){
            return false;
        }
        return numero.matches("\\d+");
    }

}
