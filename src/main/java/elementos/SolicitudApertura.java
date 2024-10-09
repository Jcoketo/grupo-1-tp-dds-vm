package elementos;

import lombok.Getter;
import personas.Colaborador;

import java.time.LocalDateTime;

public class SolicitudApertura {
    private Heladera heladera;
    private Colaborador solicitanteApertura;
    @Getter private LocalDateTime fechaSolicitud;
    @Getter private LocalDateTime aperturaFehaciente;
    @Getter private static Integer horasDeApertura = 3;


    public SolicitudApertura(Heladera heladera, Colaborador solicitanteApertura){
        this.heladera = heladera;
        this.solicitanteApertura = solicitanteApertura;
        this.fechaSolicitud = LocalDateTime.now();
    }

    public void setAperturaFehaciente(){
        this.aperturaFehaciente = LocalDateTime.now();
    }


}