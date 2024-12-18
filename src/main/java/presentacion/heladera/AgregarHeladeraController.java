package presentacion.heladera;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.consumosAPIs.servicioGeoLocalizacion.LocalizadorLatLong;
import modelo.elementos.PuntoEstrategico;
import org.jetbrains.annotations.NotNull;
import utils.GeneradorModel;

import java.util.List;
import java.util.Map;

public class AgregarHeladeraController implements Handler {


    public AgregarHeladeraController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        PuntoEstrategico punto = new PuntoEstrategico(0.0, 0.0);
        List<PuntoEstrategico> puntosRecomendados = LocalizadorLatLong.generarPuntosEstrategicosMock(punto.getLatitud(), punto.getLongitud());

        model.put("puntosRecomendados", puntosRecomendados);
        context.render("templates/agregarHeladera.mustache", model);

    }

}
