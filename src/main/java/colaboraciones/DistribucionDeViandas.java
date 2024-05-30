package colaboraciones;

import elementos.Heladera;
import enums.MotivoDistribucion;
import enums.TipoPersona;
import personas.Colaborador;

import java.time.LocalDateTime;

public class DistribucionDeViandas extends Colaboracion {
    private Heladera heladeraOrigen;
    private Heladera heladeraDestino;
    private int cantidadViandas;
    private MotivoDistribucion motivoDistribucion;
    private LocalDateTime fechaDistribucion;

    static {
        personasHabilitadas.add(TipoPersona.PH);
    }

    public DistribucionDeViandas(TipoPersona persona, Integer cantidadViandas, Heladera heladeraOrigen, Heladera heladeraDestino, MotivoDistribucion motivoDistribucion, LocalDateTime fechaDistribucion) {
        this.cantidadViandas = cantidadViandas;
        this.heladeraOrigen = heladeraOrigen;
        this.heladeraDestino = heladeraDestino;
        this.motivoDistribucion = motivoDistribucion;
        this.fechaDistribucion = LocalDateTime.now();

    }

    @Override
    public void hacerColaboracion(Colaborador colaborador) {
        //TODO
    }
}
