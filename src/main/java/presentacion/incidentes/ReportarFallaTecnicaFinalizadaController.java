package presentacion.incidentes;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;
import lombok.Getter;
import lombok.Setter;
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

        NotificacionAlerta notificacionAlerta = new NotificacionAlerta();

        try {
            repoIncidentes.agregarIncidente(falla);
            FileUtils.copyInputStreamToFile(file.content(), archivo);

        } catch (ExcepcionValidacion e) {
            notificacionAlerta.error(e.getMessage());
            context.redirect("/visualizarFallasTecnicas?heladeraId=" + idHeladera);
            return;
        }

        try {
            Tecnico tecnico = repoTecnicos.obtenerTecnicoCercano(heladera.getPuntoEstrategico().getAreas(), heladera);
            tecnico.notificarFalla(heladera, falla.getDescripcion());
        } catch (Exception e) {
            notificacionAlerta.error("Hubo un error al notificar a los tecnicos, porfavor informe a sistemas.");
            System.out.println("Error al notificar a los tecnicos, porfavor informe a sistemas."); //LOG
            context.redirect("/visualizarFallasTecnicas?heladeraId=" + idHeladera);
            return;
        }

        notificacionAlerta.aprobada("Tu reporte de falla tecnica ha sido registrada con exito!");
        context.sessionAttribute("notificacionAlerta", notificacionAlerta);
        context.redirect("/visualizarFallasTecnicas?heladeraId=" + idHeladera);
    }

}
@Getter
@Setter
class NotificacionAlerta {
    private String tipo;
    private String mensaje;

    public void aprobada(String mensaje){
        this.mensaje = mensaje;
        this.tipo = "success";
    }
    public void error(String mensaje){
        this.mensaje = mensaje;
        this.tipo = "danger";
    }
}
