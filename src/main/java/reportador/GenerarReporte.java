package reportador;

import elementos.Heladera;
import personas.Colaborador;
import repositorios.RepositorioColaboradores;
import repositorios.RepositorioHeladeras;
import repositorios.RepositorioReportes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GenerarReporte{

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void generarReporte(){
        Reporte reporteSemanal = new Reporte(obtenerFallasXHeladera(),
                                            obtenerViandasXColaborador(),
                                            obtenerViandasColocadasXHeladera(),
                                            obtenerViandasRetiradasXHeladera());

        reporteSemanal.generarTXT();

        RepositorioReportes.getInstancia().agregarReporte(reporteSemanal);

    }

    public List<ReporteHeladera> obtenerFallasXHeladera() {
        List<ReporteHeladera> reportes = new ArrayList<>();

        for (Heladera heladera : RepositorioHeladeras.getHeladeras()) {
            ReporteHeladera reporte = new ReporteHeladera();
            reporte.setHeladera(heladera);
            reporte.setCantidad(heladera.getContadorFallasSemanal());
            reportes.add(reporte);
            heladera.resetearContador("fallas");
        }


        return reportes;
    }

    public List<ReporteHeladera> obtenerViandasColocadasXHeladera() {
        List<ReporteHeladera> reportes = new ArrayList<>();

        for (Heladera heladera : RepositorioHeladeras.getHeladeras()) {
            ReporteHeladera reporte = new ReporteHeladera();
            reporte.setHeladera(heladera);
            reporte.setCantidad(heladera.getContadorViandasColocadas());
            reportes.add(reporte);
            heladera.resetearContador("colocadas");
        }

        return reportes;
    }

    public List<ReporteHeladera> obtenerViandasRetiradasXHeladera() {
        List<ReporteHeladera> reportes = new ArrayList<>();

        for (Heladera heladera : RepositorioHeladeras.getHeladeras()) {
            ReporteHeladera reporte = new ReporteHeladera();
            reporte.setHeladera(heladera);
            reporte.setCantidad(heladera.getContadorViandasRetiradas());
            reportes.add(reporte);
            heladera.resetearContador("retiradas");
        }

        return reportes;
    }

    public List<ReporteColaborador> obtenerViandasXColaborador() {
        List<ReporteColaborador> reportes = new ArrayList<>();

        for (Colaborador colab : RepositorioColaboradores.getColaboradores()) {
            ReporteColaborador reporte = new ReporteColaborador();
            reporte.setColaborador(colab);
            reporte.setCantidadViandas(colab.getContadorViandasDonadasSemanal());
            reportes.add(reporte);
            colab.resetearContadorViandasSemanales();
        }

        return reportes;
    }


    public void iniciarProgramacion() {
        scheduler.scheduleAtFixedRate(this::generarReporte, 0, 7, TimeUnit.DAYS);
    }

}
