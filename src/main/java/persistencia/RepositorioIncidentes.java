package persistencia;

import lombok.Getter;
import modelo.colaboracion.Oferta;
import modelo.elementos.FallaTecnica;
import modelo.elementos.Incidente;
import modelo.excepciones.ExcepcionValidacion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class RepositorioIncidentes {
    @Getter
    private static RepositorioIncidentes instancia = null;

    private static EntityManager em;

    private RepositorioIncidentes(EntityManager entityManager) {
        em = entityManager;
    }

    public static RepositorioIncidentes getInstancia(EntityManager entityManager) {
        if(instancia == null) {
            instancia = new RepositorioIncidentes(entityManager);
        }
        return instancia;
    }
    public static RepositorioIncidentes getInstancia() {
        if(instancia == null) {
            throw new ExcepcionValidacion("No fue instanciado en el repositorio!");
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
            throw new ExcepcionValidacion("El incidente no tiene una heladera asociada");
        }
        if (incidente.getFechaHoraIncidente() == null) {
            throw new ExcepcionValidacion("El incidente no tiene una fecha y hora asociadas");
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

    public List<FallaTecnica> obtenerIncidentes(int idHeladera) {
        TypedQuery<FallaTecnica> query = em.createQuery("SELECT i FROM FallaTecnica i WHERE i.heladera.id = :idHeladera" , FallaTecnica.class);
        query.setParameter("idHeladera", idHeladera);
        try {
            return query.getResultList();
        } catch (Exception e){
            return null;
        }

    }


}
