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

        Integer id_persona = (Integer) context.sessionAttribute("idPersona");

        PersonaJuridica persona = repoColab.traerPersonaPorIdJuridica(id_persona);

        PuntoEstrategicoXdireccion servicio = PuntoEstrategicoXdireccion.getInstancia();

        String direccion = persona.getDireccion().replace(" ", "+").replace(",", "");

        List<PuntoEstrategico> puntosRecomendados = new ArrayList<>();

        try {
            PuntoEstrategico punto = servicio.obtenerPuntoDeColocacion(direccion);
            RecomendadorDePuntos recomendador = RecomendadorDePuntos.getInstancia();
            puntosRecomendados = recomendador.obtenerPuntosRecomendados(punto.getLatitud(), punto.getLongitud(), 1000.0);

        }catch (ExcepcionValidacion e){
            model.put("errorAPI", e.getMessage());
        }

        model.put("puntosRecomendados", puntosRecomendados);
        context.render("templates/agregarHeladera.mustache", model);
    }

}
