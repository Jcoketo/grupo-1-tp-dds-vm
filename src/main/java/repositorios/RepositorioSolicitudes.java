package repositorios;

import elementos.SeguimientoApertura;

import java.util.ArrayList;
import java.util.List;

public class RepositorioSolicitudes {
    private static RepositorioSolicitudes instancia = null;

    private static List<SeguimientoApertura> solicitudes;

    RepositorioSolicitudes() {
        solicitudes = new ArrayList<SeguimientoApertura>();
    }

    public static RepositorioSolicitudes getInstancia() {
        if(instancia == null) {
            instancia = new RepositorioSolicitudes();
        }
        return instancia;
    }

    public void agregarSolicitud(SeguimientoApertura solicitud){
        solicitudes.add(solicitud);
    }

    public SeguimientoApertura buscarSolicitud()

}
