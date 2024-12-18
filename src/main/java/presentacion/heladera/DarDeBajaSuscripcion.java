package presentacion.heladera;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.personas.Colaborador;
import modelo.suscripcion.Suscripcion;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioColaboradores;
import persistencia.RepositorioSuscripciones;
import utils.GeneradorModel;

import java.util.Map;

public class DarDeBajaSuscripcion implements Handler {
    private static RepositorioColaboradores repoPersonas = RepositorioColaboradores.getInstancia();
    private static RepositorioSuscripciones repoSuscripciones = RepositorioSuscripciones.getInstancia();

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        Integer idPersona = context.sessionAttribute("idPersona");

        Colaborador colaborador = repoPersonas.buscarColaboradorXIdPersona(idPersona);

        if ( colaborador == null ) {
            context.redirect("/logout");
            return;
        }

        String idSuscrip = context.queryParam("id");

        if ( idSuscrip == null || idSuscrip.equals("") ) {
            context.redirect("/misSuscripciones");
            return;
        }

        Integer idSuscripcion;
        try {
            idSuscripcion = Integer.parseInt(idSuscrip);
        } catch (NumberFormatException e) {
            context.redirect("/misSuscripciones");
            return;
        }

        Suscripcion suscripcion = repoSuscripciones.buscarSuscripcionXId(idSuscripcion);

        if ( suscripcion == null || colaborador.getSuscripciones().stream().noneMatch(s -> s.getId() == idSuscripcion) ) {
            context.redirect("/misSuscripciones");
            return;
        }

        suscripcion.setBajaLogica(Boolean.TRUE);
        suscripcion.setHeladera(null);
        repoSuscripciones.actualizarSuscripcion(suscripcion);

        context.sessionAttribute("notificacionBajaSuscripcion", "Se ha dado de baja la suscripción correctamente.");
        context.redirect("/misSuscripciones");

    }
}
