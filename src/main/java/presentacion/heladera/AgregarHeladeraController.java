package presentacion.heladera;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.consumosAPIs.api_direccion.PuntoEstrategicoXdireccion;
import modelo.consumosAPIs.recomendadorDePuntos.RecomendadorDePuntos;
import modelo.elementos.PuntoEstrategico;
import modelo.excepciones.ExcepcionValidacion;
import modelo.personas.PersonaJuridica;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioColaboradores;
import utils.GeneradorModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgregarHeladeraController implements Handler {

    private RepositorioColaboradores repoColab = RepositorioColaboradores.getInstancia();

    public AgregarHeladeraController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        PuntoEstrategico punto = new PuntoEstrategico(0.0, 0.0);
        RecomendadorDePuntos recomendador = RecomendadorDePuntos.getInstancia();
        List<PuntoEstrategico> puntosRecomendados = recomendador.obtenerPuntosRecomendados(punto.getLatitud(), punto.getLongitud(), 1000.0);
        model.put("puntosRecomendados", puntosRecomendados);
        context.render("templates/agregarHeladera.mustache", model);

    }

}
