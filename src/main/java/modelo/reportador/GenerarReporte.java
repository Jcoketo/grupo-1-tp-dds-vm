package modelo.reportador;

import modelo.elementos.Heladera;
import modelo.personas.Colaborador;
import persistencia.RepositorioColaboradores;
import persistencia.RepositorioHeladeras;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GenerarReporte{

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public GrupoReporte generarReporte(){
        GrupoReporte grupoReporte = new GrupoReporte();
        grupoReporte.agregarReporte(generarReporteColaborador());
        grupoReporte.agregarReporte(generarReporteHeladeraFallas());
        grupoReporte.agregarReporte(generarReporteHeladeraViandas());
        return grupoReporte;
    }

    public ReporteHeladeraViandas generarReporteHeladeraViandas() {
        Map<Heladera, Integer> colocadas = new HashMap<>();
        Map<Heladera, Integer> retiradas = new HashMap<>();
        for (Heladera heladera : RepositorioHeladeras.getInstancia().obtenerHeladeras()) {
            colocadas.put(heladera, heladera.getContadorViandasColocadas());
            retiradas.put(heladera, heladera.getContadorViandasRetiradas());
            heladera.resetearContador("colocadas");
            heladera.resetearContador("retiradas");
        }
        return new ReporteHeladeraViandas(colocadas, retiradas);
    }

    public ReporteHeladeraFallas generarReporteHeladeraFallas() {
        Map<Heladera, Integer> datos = new HashMap<>();
        for (Heladera heladera : RepositorioHeladeras.getInstancia().obtenerHeladeras()) {
            datos.put(heladera, heladera.getContadorFallasSemanal());
            heladera.resetearContador("fallas");
        }
        return new ReporteHeladeraFallas(datos);
    }

    public ReporteColaborador generarReporteColaborador() {
        Map<Colaborador, Integer> datos = new HashMap<>();
        for (Colaborador colab : RepositorioColaboradores.obtenerColaboradores()) {
            datos.put(colab, colab.getContadorViandasDonadasSemanal());
            colab.resetearContadorViandasSemanales();
        }
        return new ReporteColaborador(datos);
    }

    public void iniciarProgramacion() {
        scheduler.scheduleAtFixedRate(this::generarReporte, 0, 7, TimeUnit.DAYS);
    }

}
/*
1.BOTON "GENERAR REPORTES" Y CRON TASK SEMANAL -> LLAMAN A GenerarReporte.generarReporte().
2.CAMBIAR RepositorioReportes A RepositorioGrupoReportes.
3.BOTONES "DESCARGAR REPORTE X" -> DEBEN SER DEL TIPO <a> CON href="URL/PATH DEL REPORTE" y download="NOMBRE DEL ARCHIVO PARA DESCARGARSE".
Ejemplo: <a href="/path/to/file.pdf" download="CustomFileName.pdf">Download PDF</a>
*/
