package presentacion.reportes;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.Getter;
import lombok.Setter;
import modelo.reportador.GrupoReporte;
import modelo.reportador.ReporteColaborador;
import modelo.reportador.ReporteHeladeraFallas;
import modelo.reportador.ReporteHeladeraViandas;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioGrupoReportes;
import utils.GeneradorModel;

import java.time.LocalDate;
import java.util.Map;

public class MisReportesController implements Handler {
    private static RepositorioGrupoReportes repoReportes = RepositorioGrupoReportes.getInstancia();

    public MisReportesController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        GrupoReporte grupoReportes = repoReportes.obtenerUltimoGrupoReporte();

        if(grupoReportes == null){
            grupoReportes = new GrupoReporte();
        }

        DatosGrupoReportes datosGrupoReportes = this.getDatosGrupoReportes(grupoReportes);

        model.put("grupoReportes", datosGrupoReportes);

        context.render("templates/misReportes.mustache", model);
    }

    public DatosGrupoReportes getDatosGrupoReportes(GrupoReporte grupoReportes) {
        ReporteColaborador reporteColaborador = grupoReportes.obtenerReporteColaborador();
        ReporteHeladeraFallas reporteHeladeraFallas = grupoReportes.obtenerReporteHeladeraFallas();
        ReporteHeladeraViandas reporteHeladeraViandas = grupoReportes.obtenerReporteHeladeraViandas();

        DatosGrupoReportes reportes =  new DatosGrupoReportes(LocalDate.now(), "", "", "");

        if ( reporteColaborador != null ) {
            reportes.setPathReporteColaborador(reporteColaborador.getPath());
            reportes.setHayDatos(true); }

        if ( reporteHeladeraFallas != null ){
            reportes.setPathReporteHeladeraFallas(reporteHeladeraFallas.getPath());
            reportes.setHayDatos(true); }

        if ( reporteHeladeraViandas != null ){
            reportes.setPathReporteHeladeraViandas(reporteHeladeraViandas.getPath());
            reportes.setHayDatos(true); }

        return reportes;
    }
}

@Getter
@Setter
class DatosGrupoReportes {
    private Boolean hayDatos = false;
    private LocalDate fechaCreacion;
    private String pathReporteColaborador;
    private String pathReporteHeladeraFallas;
    private String pathReporteHeladeraViandas;

    public DatosGrupoReportes(LocalDate fechaCreacion, String pathReporteColaborador, String pathReporteHeladeraFallas, String pathReporteHeladeraViandas) {
        this.fechaCreacion = fechaCreacion;
        this.pathReporteColaborador = pathReporteColaborador;
        this.pathReporteHeladeraFallas = pathReporteHeladeraFallas;
        this.pathReporteHeladeraViandas = pathReporteHeladeraViandas;
    }
}


