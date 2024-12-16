package presentacion.heladera;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.personas.PersonaJuridica;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioColaboradores;
import utils.GeneradorModel;

import java.util.Map;

public class DarDeBajaMiHeladeraController implements Handler {

    private static RepositorioColaboradores repoColaboradores = RepositorioColaboradores.getInstancia();

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        Integer idPersona = context.sessionAttribute("idPersona");

        String idHel = context.queryParam("heladeraId");

        if (idHel == null || idHel.equals("")) {
            context.sessionAttribute("mensajeBaja", "No se ha seleccionado ninguna heladera para dar de baja.");
            context.redirect("/misHeladeras");
        }

        Integer idHeladera = Integer.parseInt(idHel);

        PersonaJuridica persona = repoColaboradores.traerPersonaPorIdJuridica(idPersona);
        if ( ! (persona.getHeladeras().stream().anyMatch(h -> h.getId() == idHeladera) ) ) {
            context.sessionAttribute("mensajeBaja", "La heladera indicada no pertenece a su organización.");
            context.redirect("/misHeladeras");
        }

        persona.getHeladeras().stream().filter(h -> h.getId() == idHeladera).findFirst().get().setBajaLogica(Boolean.TRUE);
        repoColaboradores.actualizarPersona(persona);

        context.redirect("/misHeladeras");

    }
}
