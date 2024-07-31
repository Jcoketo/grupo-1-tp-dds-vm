package reportador;

import elementos.Heladera;
import personas.Colaborador;

import java.util.Map;

public class Reporte {
    private Map<Heladera, Integer> cantidadFallasXHeladera;
    private Map<Colaborador, Integer> viandasXColaborador;
    private Map<Heladera, Integer> cantidadViandasColocadas;
    private Map<Heladera, Integer> cantidadViandasRetiradas;

    public Reporte(Map<Heladera, Integer> cantidadFallasXHeladera, Map<Colaborador, Integer> viandasXColaborador, Map<Heladera, Integer> cantidadViandasColocadas, Map<Heladera, Integer> cantidadViandasRetiradas) {
        this.cantidadFallasXHeladera = cantidadFallasXHeladera;
        this.viandasXColaborador = viandasXColaborador;
        this.cantidadViandasColocadas = cantidadViandasColocadas;
        this.cantidadViandasRetiradas = cantidadViandasRetiradas;
    }

    public void generarTXT(){
        //TODO
    }
}
