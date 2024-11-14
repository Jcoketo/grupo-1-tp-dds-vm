package modelo.reportador;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class Reporte {

    private int id;

    private LocalDateTime fechaReporte;

    private String cantidadFallasXHeladera;

    private String viandasXColaborador;

    private String cantidadViandasColocadas;

    private String cantidadViandasRetiradas;


    public Reporte(String cantidadFallasXHeladera, String viandasXColaborador, String cantidadViandasColocadas, String cantidadViandasRetiradas) {
        this.cantidadFallasXHeladera = cantidadFallasXHeladera;
        this.viandasXColaborador = viandasXColaborador;
        this.cantidadViandasColocadas = cantidadViandasColocadas;
        this.cantidadViandasRetiradas = cantidadViandasRetiradas;
    }

    public Reporte() {

    }

    public void generarTXT(){
        //TODO
        /*hacer la proxima entrega!!!! */
    }
}
