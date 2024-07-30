package elementos;

import lombok.Setter;
import personas.Colaborador;
import java.time.LocalDateTime;

public class SeguimientoApertura {
    //EstadoSolicitud estadoSolicitud;
    private Heladera heladera;
    private Colaborador solicitanteApertura;
    private LocalDateTime fechaSolicitud;
    private TipoSolicitud tipoSolicitud;
    @Setter
    private LocalDateTime aperturaFehaciente;


    public SeguimientoApertura(Heladera heladera, Colaborador solicitanteApertura, TipoSolicitud tipoSolicitud) {
        this.heladera = heladera;
        this.solicitanteApertura = solicitanteApertura;
        this.fechaSolicitud = LocalDateTime.now();
        this.tipoSolicitud = tipoSolicitud;
        //this.estadoSolicitud = EstadoSolicitud.PENDIENTE;
    }
}
