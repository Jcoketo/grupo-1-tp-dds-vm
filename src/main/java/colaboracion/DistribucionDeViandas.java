package colaboracion;

import elementos.Heladera;
import personas.TipoPersona;
import personas.Colaborador;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

public class DistribucionDeViandas extends Colaboracion {
    private Heladera heladeraOrigen;
    private Heladera heladeraDestino;
    private int cantidadViandas;
    private MotivoDistribucion motivoDistribucion;
    private LocalDate fechaDistribucion;

    public DistribucionDeViandas(TipoPersona persona, Integer cantidadViandas, Heladera heladeraOrigen, Heladera heladeraDestino, MotivoDistribucion motivoDistribucion) {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PH);
        this.cantidadViandas = cantidadViandas;
        this.heladeraOrigen = heladeraOrigen;
        this.heladeraDestino = heladeraDestino;
        this.motivoDistribucion = motivoDistribucion;
        this.fechaDistribucion = LocalDate.now();
    }

    @Override
    public void hacerColaboracion(Colaborador colaborador) {
        //TODO
    }

    @Override
    public boolean validar(Colaborador colaborador){
        //TODO
        return true;
    }

    @Override
    public void incrementarPuntos(Colaborador colaborador){
        //TODO
    }
}
