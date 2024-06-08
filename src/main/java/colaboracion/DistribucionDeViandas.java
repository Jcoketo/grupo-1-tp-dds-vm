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
    private LocalDateTime fechaDistribucion;

    public DistribucionDeViandas(TipoPersona persona, Integer cantidadViandas, Heladera heladeraOrigen, Heladera heladeraDestino, MotivoDistribucion motivoDistribucion) {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PH);
        this.cantidadViandas = cantidadViandas;
        this.heladeraOrigen = heladeraOrigen;
        this.heladeraDestino = heladeraDestino;
        this.motivoDistribucion = motivoDistribucion;
        this.fechaDistribucion = LocalDateTime.now();
    }
    // CONSTRUCTOR PARA EL IMPORTADOR DE SCV
    public DistribucionDeViandas(LocalDateTime fechaDistribucion, Integer cantidadViandas)
    {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PH);
        this.fechaDonacion  = fechaDistribucion;
        this.fechaDistribucion = fechaDistribucion;
        this.cantidadViandas = cantidadViandas;

        this.heladeraDestino = null;
        this.heladeraOrigen = null;
        this.motivoDistribucion = null;

    }

    @Override
    public void hacerColaboracion(Colaborador colaborador) {
        //TODO
    }

    @Override
    public boolean validar(Colaborador colaborador){
        return this.tiposPersonasHabilitadas.contains(colaborador.getTipo());
    }

    @Override
    public void incrementarPuntos(Colaborador colaborador){ colaborador.incrementarPuntaje((float) this.cantidadViandas); }


}

