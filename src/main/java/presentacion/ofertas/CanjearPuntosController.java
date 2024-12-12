package presentacion.ofertas;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.colaboracion.Oferta;
import modelo.colaboracion.TipoOferta;
import modelo.personas.Colaborador;
import modelo.personas.TipoPersona;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioColaboradores;
import persistencia.RepositorioOfertas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CanjearPuntosController implements Handler {

    RepositorioColaboradores repoColaboradores;
    RepositorioOfertas repoOfertas;

    public CanjearPuntosController(RepositorioColaboradores repoColaboradores, RepositorioOfertas repositorioOfertas) {
        super();
        this.repoColaboradores = repoColaboradores;
        this.repoOfertas = repositorioOfertas;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = context.sessionAttribute("model");
        if (model == null) {
            model = new HashMap<>();
            context.sessionAttribute("model", model);
        }


        Integer idPersona = context.sessionAttribute("idPersona");
        TipoPersona tipoPer = context.sessionAttribute("tipoPersona");
        Colaborador colab = repoColaboradores.buscarColaboradorXIdPersona(idPersona);

        String tipoOferta = context.queryParam("tipoOferta");
        List<Oferta> ofertas = repoOfertas.conocerOfertasDisponibles();

        TipoPersona tipoPersona = context.sessionAttribute("tipoPersona");

        Boolean esPersonaJuridica = tipoPersona.equals(TipoPersona.PJ);

        model.put("esPersonaJuridica",esPersonaJuridica);

        if(tipoOferta == null){
            model.put("puntos", colab.getPuntaje());
            model.put("ofertas", ofertas);

            context.render("templates/canjearPuntos.mustache",model);
            return;
        }

        switch (tipoOferta) {
            case "producto":
                ofertas = ofertas.stream().filter(oferta -> oferta.getTipoOferta() == TipoOferta.PRODUCTO).collect(Collectors.toList());
                break;
            case "servicio":
                ofertas = ofertas.stream().filter(oferta -> oferta.getTipoOferta() == TipoOferta.SERVICIO).collect(Collectors.toList());
                break;
            default:
                break;
        }


        model.put("puntos", colab.getPuntaje());
        model.put("ofertas", ofertas);

        context.render("templates/canjearPuntos.mustache",model);
    }

}
