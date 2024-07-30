package elementos;

import lombok.Getter;
import lombok.Setter;
import personas.Colaborador;

import javax.ws.rs.GET;
import java.time.LocalDateTime;

public class SeguimientoApertura {
    //EstadoSolicitud estadoSolicitud;
    private Heladera heladera;
    private Colaborador solicitanteApertura;
    @Getter private LocalDateTime fechaSolicitud;
    private TipoSolicitud tipoSolicitud;
    @Setter
    @Getter private LocalDateTime aperturaFehaciente;

    @Getter private static Integer horasDeApertura = 3;


    public SeguimientoApertura(Heladera heladera, Colaborador solicitanteApertura, TipoSolicitud tipoSolicitud) {
        this.heladera = heladera;
        this.solicitanteApertura = solicitanteApertura;
        this.fechaSolicitud = LocalDateTime.now();
        this.tipoSolicitud = tipoSolicitud;
        //this.estadoSolicitud = EstadoSolicitud.PENDIENTE;
    }

    public void setAperturaFehaciente(){
        this.aperturaFehaciente = LocalDateTime.now();
    }


}
