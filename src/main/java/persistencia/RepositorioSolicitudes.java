package persistencia;

import lombok.Getter;
import modelo.elementos.SolicitudApertura;
import javax.persistence.EntityManager;


public class RepositorioSolicitudes {
    @Getter
    private static RepositorioSolicitudes instancia = null;

    private static EntityManager em;

    public RepositorioSolicitudes(EntityManager entityManager ){
        em = entityManager;
    }

    public static RepositorioSolicitudes getInstancia(EntityManager entityManager) {
        if(instancia == null) {
            instancia = new RepositorioSolicitudes(entityManager);
        }
        return instancia;
    }

    public void agregarSolicitud(SolicitudApertura solicitud){

    }

    public void cambiarEstadoAFehaciente(SolicitudApertura solicitud){
        solicitud.setAperturaFehaciente();
    }

}
