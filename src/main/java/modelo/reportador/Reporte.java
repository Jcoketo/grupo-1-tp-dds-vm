package modelo.reportador;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "reporte")
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter private int id;

    @Getter
    @Column(name = "link", nullable = false)
    public String link;

    private LocalDateTime fechaReporte;

    private String cantidadFallasXHeladera;

    private String viandasXColaborador;

    private String cantidadViandasColocadas;

    private String cantidadViandasRetiradas;


    public Reporte( String cantidadFallasXHeladera, String viandasXColaborador, String cantidadViandasColocadas, String cantidadViandasRetiradas) {

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

    public void generarLink(){
        //TODO
    }
}
