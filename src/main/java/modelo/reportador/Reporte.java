package modelo.reportador;

import java.util.List;

public class Reporte {
    private List<ReporteHeladera> cantidadFallasXHeladera;
    private List<ReporteColaborador> viandasXColaborador;
    private List<ReporteHeladera> cantidadViandasColocadas;
    private List<ReporteHeladera> cantidadViandasRetiradas;

    public Reporte(List<ReporteHeladera> cantidadFallasXHeladera, List<ReporteColaborador> viandasXColaborador, List<ReporteHeladera> cantidadViandasColocadas, List<ReporteHeladera> cantidadViandasRetiradas) {
        this.cantidadFallasXHeladera = cantidadFallasXHeladera;
        this.viandasXColaborador = viandasXColaborador;
        this.cantidadViandasColocadas = cantidadViandasColocadas;
        this.cantidadViandasRetiradas = cantidadViandasRetiradas;
    }

    public void generarTXT(){
        //TODO
    }
}
