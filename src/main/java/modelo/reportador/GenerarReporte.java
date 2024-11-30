package modelo.reportador;

import modelo.elementos.Heladera;
import modelo.personas.Colaborador;
import persistencia.RepositorioColaboradores;
import persistencia.RepositorioHeladeras;
import persistencia.RepositorioReportes;

import java.util.ArrayList;
import java.util.List;
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

    public String obtenerFallasXHeladera() {
        List<ReporteHeladera> reportes = new ArrayList<>();

        for (Heladera heladera : RepositorioHeladeras.obtenerHeladeras()) {
            ReporteHeladera reporte = new ReporteHeladera();
            reporte.setHeladera(heladera);
            reporte.setCantidad(heladera.getContadorFallasSemanal());
            reportes.add(reporte);
            heladera.resetearContador("fallas");
        }

        String rutaDelArchivo = "ruta";

        return rutaDelArchivo;
    }

    public String obtenerViandasColocadasXHeladera() {
        List<ReporteHeladera> reportes = new ArrayList<>();

        for (Heladera heladera : RepositorioHeladeras.obtenerHeladeras()) {
            ReporteHeladera reporte = new ReporteHeladera();
            reporte.setHeladera(heladera);
            reporte.setCantidad(heladera.getContadorViandasColocadas());
            reportes.add(reporte);
            heladera.resetearContador("colocadas");
        }

        String rutaDelArchivo = "ruta";

        return rutaDelArchivo;
    }

    public String obtenerViandasRetiradasXHeladera() {
        List<ReporteHeladera> reportes = new ArrayList<>();

        for (Heladera heladera : RepositorioHeladeras.obtenerHeladeras()) {
            ReporteHeladera reporte = new ReporteHeladera();
            reporte.setHeladera(heladera);
            reporte.setCantidad(heladera.getContadorViandasRetiradas());
            reportes.add(reporte);
            heladera.resetearContador("retiradas");
        }

        String rutaDelArchivo = "ruta";

        return rutaDelArchivo;
    }

    public String obtenerViandasXColaborador() {
        List<ReporteColaborador> reportes = new ArrayList<>();

        for (Colaborador colab : RepositorioColaboradores.obtenerColaboradores()) {
            ReporteColaborador reporte = new ReporteColaborador();
            reporte.setColaborador(colab);
            reporte.setCantidadViandas(colab.getContadorViandasDonadasSemanal());
            reportes.add(reporte);
            colab.resetearContadorViandasSemanales();
        }

        String rutaDelArchivo = "ruta";

        return rutaDelArchivo;
    }


    public void iniciarProgramacion() {
        scheduler.scheduleAtFixedRate(this::generarReporte, 0, 7, TimeUnit.DAYS);
    }

}
