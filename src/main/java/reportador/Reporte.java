package reportador;

import elementos.Heladera;
import personas.Colaborador;

import java.util.List;
import java.util.Map;

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
