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


    // CONSTRUCTOR PRINCIPAL
    public DistribucionDeViandas(TipoPersona persona, Integer cantidadViandas, Heladera heladeraOrigen, Heladera heladeraDestino, MotivoDistribucion motivoDistribucion, LocalDate fechaDistribucion) {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PH);
        this.heladeraOrigen = heladeraOrigen;
        this.heladeraDestino = heladeraDestino;
        this.cantidadViandas = cantidadViandas;
        this.motivoDistribucion = motivoDistribucion;
        this.fechaColaboracion = fechaDistribucion;
    }

    // CONSTRUCTOR PARA IMPORTADOR CSV
    public DistribucionDeViandas(LocalDate fechaDistribucion, Integer cantidadViandas)
    {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PH);
        this.fechaColaboracion  = fechaDistribucion;
        this.cantidadViandas = cantidadViandas;

        this.heladeraDestino = null;
        this.heladeraOrigen = null;
        this.motivoDistribucion = null;

    }

    @Override
    public void hacerColaboracion(Colaborador colaborador) {
        //TODO
        incrementarPuntos(colaborador);
    }

    @Override
    public boolean validar(Colaborador colaborador){
        return this.tiposPersonasHabilitadas.contains(colaborador.getTipo());
    }

    @Override
    public void incrementarPuntos(Colaborador colaborador){ colaborador.incrementarPuntaje((float) this.cantidadViandas); }


}

