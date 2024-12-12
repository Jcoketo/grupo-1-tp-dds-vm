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
import java.util.stream.Collectors;

public class MisCanjesController implements Handler {

    RepositorioColaboradores repoColaboradores;
    RepositorioOfertas repoOfertas;

    public MisCanjesController(RepositorioColaboradores repoColaboradores, RepositorioOfertas repositorioOfertas) {
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
        Colaborador colab = repoColaboradores.buscarColaboradorXIdPersona(idPersona);

        List<Oferta> ofertas = colab.getCanjesRealizados();



        model.put("puntos", colab.getPuntaje());
        model.put("ofertas", ofertas);


        context.render("templates/misCanjes.mustache",model);
    }
}
