package reportador;

import elementos.Heladera;
import personas.Colaborador;
import repositorios.RepositorioColaboradores;
import repositorios.RepositorioHeladeras;

import java.util.HashMap;
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
    }

    public Map<Heladera, Integer> obtenerFallasXHeladera(){
        Map<Heladera, Integer> map = new HashMap<>();
        for (Heladera heladera : RepositorioHeladeras.getHeladeras()) {
            map.put(heladera, heladera.getContadorFallasSemanal());
            heladera.resetearContador("fallas");
        }
        return map;
    }

    public Map<Heladera, Integer> obtenerViandasColocadasXHeladera(){
        Map<Heladera, Integer> map = new HashMap<>();
        for (Heladera heladera : RepositorioHeladeras.getHeladeras()) {
            map.put(heladera, heladera.getContadorViandasColocadas());
            heladera.resetearContador("colocadas");
        }
        return map;
    }

    public Map<Heladera, Integer> obtenerViandasRetiradasXHeladera() {
        Map<Heladera, Integer> map = new HashMap<>();
        for (Heladera heladera : RepositorioHeladeras.getHeladeras()) {
            map.put(heladera, heladera.getContadorViandasRetiradas());
            heladera.resetearContador("retiradas");
        }
        return map;
    }

    public Map<Colaborador, Integer> obtenerViandasXColaborador(){
        Map<Colaborador, Integer> map = new HashMap<>();
        for (Colaborador colab : RepositorioColaboradores.getColaboradores()) {
            map.put(colab, colab.getContadorViandasDonadasSemanal());
            colab.resetearContadorViadasSemanales();
        }
        return map;
    }

    public void iniciarProgramacion() {
        scheduler.scheduleAtFixedRate(this::generarReporte, 0, 7, TimeUnit.DAYS);
    }

}
