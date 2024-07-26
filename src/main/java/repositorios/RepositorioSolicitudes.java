package repositorios;

import elementos.Incidente;
import elementos.SolicitudApertura;
import java.util.List;

public class RepositorioSolicitudes {
    public List<SolicitudApertura> solicitudes;

    public void agregar(SolicitudApertura solicitud){
        solicitudes.add(solicitud);
    }
}
