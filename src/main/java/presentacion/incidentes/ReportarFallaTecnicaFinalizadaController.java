package presentacion.incidentes;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;
import modelo.elementos.FallaTecnica;
import modelo.elementos.Heladera;
import modelo.excepciones.ExcepcionValidacion;
import modelo.personas.Colaborador;
import modelo.personas.Tecnico;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioColaboradores;
import persistencia.RepositorioHeladeras;
import persistencia.RepositorioIncidentes;
import persistencia.RepositoriosTecnicos;
import utils.GeneradorModel;

import java.io.File;
import java.util.HashMap;
import java.util.List;
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
        Map<String, Object> model = GeneradorModel.getModel(context);

        Integer idPersona = context.sessionAttribute("idPersona");

        String idHel = context.formParam("heladeraId");
        Integer idHeladera = Integer.parseInt(idHel);

        String descripcionFalla = context.formParam("descripcion");
        List<UploadedFile> uploadedFiles = context.uploadedFiles("file");

        UploadedFile file = uploadedFiles.get(0);
        String fileName = file.filename();
        System.out.println("Received file: " + fileName);

        Heladera heladera = repoHeladeras.buscarHeladera(idHeladera);
        Colaborador colaborador = repoColaboradores.buscarColaboradorXIdPersona(idPersona);

        FallaTecnica falla = new FallaTecnica(heladera, colaborador, descripcionFalla, fileName);

        File archivo = new File("src/main/resources/uploads/incidentes/" + file.filename());

        try {
            repoIncidentes.agregarIncidente(falla);
            FileUtils.copyInputStreamToFile(file.content(), archivo);

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
