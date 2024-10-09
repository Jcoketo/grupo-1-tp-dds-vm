package persistencia;

import modelo.elementos.SolicitudApertura;

import java.util.ArrayList;
import java.util.List;

public class RepositorioSolicitudes {
    private static RepositorioSolicitudes instancia = null;

    private static List<SolicitudApertura> solicitudes;

    public RepositorioSolicitudes() {
        solicitudes = new ArrayList<SolicitudApertura>();
    }

    public static RepositorioSolicitudes getInstancia() {
        if(instancia == null) {
            instancia = new RepositorioSolicitudes();
        }
        return instancia;
    }

    public void agregarSolicitud(SolicitudApertura solicitud){
        solicitudes.add(solicitud);
    }

    public void cambiarEstadoAFehaciente(SolicitudApertura solicitud){
        solicitud.setAperturaFehaciente();
    }

}
