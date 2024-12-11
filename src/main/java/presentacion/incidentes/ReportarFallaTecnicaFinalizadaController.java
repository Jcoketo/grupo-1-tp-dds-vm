package presentacion.incidentes;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.elementos.FallaTecnica;
import modelo.elementos.Heladera;
import modelo.excepciones.ExcepcionValidacion;
import modelo.personas.Colaborador;
import modelo.personas.Tecnico;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioColaboradores;
import persistencia.RepositorioHeladeras;
import persistencia.RepositorioIncidentes;
import persistencia.RepositoriosTecnicos;

import java.util.HashMap;
import java.util.Map;

public class ReportarFallaTecnicaFinalizadaController implements Handler {

    private RepositorioIncidentes repoIncidentes = RepositorioIncidentes.getInstancia();
    private RepositorioHeladeras repoHeladeras = RepositorioHeladeras.getInstancia();
    private RepositorioColaboradores repoColaboradores = RepositorioColaboradores.getInstancia();
    private RepositoriosTecnicos repoTecnicos = RepositoriosTecnicos.getInstancia();


    public ReportarFallaTecnicaFinalizadaController() {
        super();
    }


    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = context.sessionAttribute("model");
        if (model == null) {
            model = new HashMap<>();
            context.sessionAttribute("model", model);
        }

        Integer idPersona = context.sessionAttribute("idPersona");
        Integer idHeladera = context.sessionAttribute("idHeladera");
        String descripcionFalla = context.sessionAttribute("descripcionFalla");
        String urlFoto = context.sessionAttribute("urlFoto");

        Heladera heladera = repoHeladeras.buscarHeladera(idHeladera);
        Colaborador colaborador = repoColaboradores.buscarColaboradorXIdPersona(idPersona);

        FallaTecnica falla = new FallaTecnica(heladera, colaborador, descripcionFalla, urlFoto);
        try {
            repoIncidentes.agregarIncidente(falla);

        } catch (ExcepcionValidacion e) {
            model.put("mensaje", "Error de validación al reportar la falla técnica");
            context.redirect("/reportarFallaTecnica");
            return;
        }

        try {
            Tecnico tecnico = repoTecnicos.obtenerTecnicoCercano(heladera.getPuntoEstrategico().getAreas(), heladera);
            tecnico.notificarFalla(heladera, falla.getDescripcion());
        } catch (Exception e) {
            model.put("mensaje", "Error al notificar a los tecnicos, porfavor informe a sistemas.");
            context.redirect("/reportarFallaTecnica");
            return;
        }

        context.redirect("/aceptarReportarFalla");
    }

}
