package presentacion.heladera;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.elementos.Heladera;
import modelo.personas.PersonaJuridica;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioColaboradores;
import persistencia.RepositorioHeladeras;

public class DarDeBajaMiHeladeraController implements Handler {

    private static RepositorioColaboradores repoColaboradores = RepositorioColaboradores.getInstancia();
    private static RepositorioHeladeras repoHeladeras = RepositorioHeladeras.getInstancia();

    @Override
    public void handle(@NotNull Context context) throws Exception {

        Integer idPersona = context.sessionAttribute("idPersona");

        String idHel = context.queryParam("heladeraId");

        if (idHel == null || idHel.equals("")) {
            context.sessionAttribute("mensajeBaja", "No se ha seleccionado ninguna heladera para dar de baja.");
            context.redirect("/misHeladeras");
            return;
        }

        PersonaJuridica persona = repoColaboradores.traerPersonaPorIdJuridica(idPersona);

        if ( persona == null || persona.getHeladeras() == null) {
            context.sessionAttribute("mensajeBaja", "No se ha podido dar de baja la heladera. Avise a sistemas.");
            context.redirect("/misHeladeras");
            return;
        }

        try {
            int idHeladera = Integer.parseInt(idHel);

            if ( persona.getHeladeras().stream().noneMatch(h -> h.getId() == idHeladera && !h.getBajaLogica()) ) {
                context.sessionAttribute("mensajeBaja", "Heladera invalida. No se ha podido dar de baja la heladera.");
                context.redirect("/misHeladeras");
            }
            Heladera heladera = persona.getHeladeras().stream().filter(h -> h.getId() == idHeladera).findFirst().get();

            heladera.darmeDeBaja();
            repoColaboradores.actualizarPersona(persona);
            repoHeladeras.actualizarHeladera(heladera);
        }
        catch (Exception e){
            context.sessionAttribute("mensajeBaja", "No se ha podido dar de baja la heladera. Avise a sistemas.");
            context.redirect("/misHeladeras");
            return;
        }

        context.redirect("/misHeladeras");

    }
}
