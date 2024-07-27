package elementos;

import personas.Colaborador;
import java.time.LocalDateTime;

public class SeguimientoApertura {
    //EstadoSolicitud estadoSolicitud;
    Heladera heladera;
    Colaborador solicitanteApertura;
    LocalDateTime fechaSolicitud;
    TipoSolicitud tipoSolicitud;

    public SeguimientoApertura(Heladera heladera, Colaborador solicitanteApertura, TipoSolicitud tipoSolicitud) {
        this.heladera = heladera;
        this.solicitanteApertura = solicitanteApertura;
        this.fechaSolicitud = LocalDateTime.now();
        this.tipoSolicitud = tipoSolicitud;
        //this.estadoSolicitud = EstadoSolicitud.PENDIENTE;
    }
}
