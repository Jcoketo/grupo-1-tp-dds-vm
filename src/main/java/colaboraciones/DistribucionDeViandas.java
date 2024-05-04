package colaboraciones;

import elementos.Heladera;
import enums.MotivoDistribucion;
import enums.TipoPersona;

import java.time.LocalDateTime;

public class DistribucionDeViandas extends Colaboracion{
    private Integer cantidadViandas;
    private Heladera heladeraOrigen;
    private Heladera heladeraDestino;
    private MotivoDistribucion motivoDistribucion;
    private LocalDateTime fechaDistribucion;

    public DistribucionDeViandas(TipoPersona persona, Integer cantidadViandas, Heladera heladeraOrigen, Heladera heladeraDestino, MotivoDistribucion motivoDistribucion, LocalDateTime fechaDistribucion) {
        this.cantidadViandas = cantidadViandas;
        this.heladeraOrigen = heladeraOrigen;
        this.heladeraDestino = heladeraDestino;
        this.motivoDistribucion = motivoDistribucion;
        this.fechaDistribucion = LocalDateTime.now();

        super.personasHabilitadas.add(TipoPersona.PH);

        this.persona = persona;
    }

    @Override
    public void hacerColaboracion() {
        try {
            this.validarPersona(persona);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //TODO
    }
}
