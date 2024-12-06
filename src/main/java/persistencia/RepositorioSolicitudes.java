package persistencia;

import lombok.Getter;
import modelo.elementos.SolicitudApertura;
import modelo.excepciones.ExcepcionValidacion;

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
    public static RepositorioSolicitudes getInstancia() {
        if(instancia == null) {
            throw new ExcepcionValidacion("No fue instanciado en el repositorio!");
        }
        return instancia;
    }

    public void agregarSolicitud(SolicitudApertura solicitud){

    }

    public void cambiarEstadoAFehaciente(SolicitudApertura solicitud){
        solicitud.setAperturaFehaciente();
        // TODO: Hay que reflejar en la BD, este cambio de estado en las solicitudes persistidas.
    }

}
