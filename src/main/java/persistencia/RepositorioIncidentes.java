package persistencia;

import modelo.colaboracion.Oferta;
import modelo.elementos.Incidente;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class RepositorioIncidentes {
    private static RepositorioIncidentes instancia = null;
    private static EntityManagerFactory emf;
    private static EntityManager em;

    private RepositorioIncidentes() {
        emf = Persistence.createEntityManagerFactory("db");
        em = emf.createEntityManager();
    }

    public static RepositorioIncidentes getInstancia() {
        if(instancia == null) {
            instancia = new RepositorioIncidentes();
        }
        return instancia;
    }
    public void agregarIncidente(Incidente incidente){
        validarInsertIncidente(incidente);
        em.getTransaction().begin();
        em.persist(incidente);
        em.getTransaction().commit();
    }

    public void validarInsertIncidente(Incidente incidente) {
        if (incidente.getHeladera() == null) {
            throw new RuntimeException("El incidente no tiene una heladera asociada");
        }
        if (incidente.getFechaHoraIncidente() == null) {
            throw new RuntimeException("El incidente no tiene una fecha y hora asociadas");
        }
    }

    public void eliminar(Incidente incidente) {
        em.getTransaction().begin();
        Incidente managedIncidente = em.find(Incidente.class, incidente.getId());
        if (managedIncidente != null) {
            em.remove(managedIncidente);
            em.getTransaction().commit();
        }
    }


}
