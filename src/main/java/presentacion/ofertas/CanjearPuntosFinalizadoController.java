package presentacion.ofertas;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.authService.AuthServiceColaboracion;
import modelo.colaboracion.Oferta;
import modelo.excepciones.ExcepcionCanjear;
import modelo.personas.Colaborador;
import org.apache.commons.io.FileUtils;
import persistencia.RepositorioColaboradores;
import persistencia.RepositorioOfertas;
import utils.GeneradorModel;

import java.util.HashMap;
import java.util.Map;

public class CanjearPuntosFinalizadoController  implements Handler {

    RepositorioColaboradores repoColaboradores = RepositorioColaboradores.getInstancia();
    RepositorioOfertas repoOfertas = RepositorioOfertas.getInstancia();

    public CanjearPuntosFinalizadoController() {
        super();
    }

    @Override
    public void handle(Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        Integer idOferta = Integer.parseInt(context.formParam("ofertaId"));
        Integer idPersona = context.sessionAttribute("idPersona");

        try {

            Colaborador colab = repoColaboradores.buscarColaboradorXIdPersona(idPersona);
            Oferta oferta = repoOfertas.buscarOfertaXId(idOferta);
            colab.canjearPuntos(oferta);

            repoOfertas.darDeBaja(oferta);

            repoColaboradores.actualizarColaborador(colab);

        } catch (ExcepcionCanjear e) {
            context.render("templates/noTienePuntosSuficientes.mustache");
            return;
        }

        context.render("templates/productoCanjeado.mustache");

        //Se canjeo el producto exitosamente

    }

}
