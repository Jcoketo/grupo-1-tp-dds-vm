package persistencia;

import lombok.Getter;
import modelo.elementos.Heladera;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public class RepositorioHeladeras {
    @Getter
    private static RepositorioHeladeras instancia = null;

    private static EntityManager em;

    private RepositorioHeladeras(EntityManager entityManager) {
        em = entityManager;
    }

    public static RepositorioHeladeras getInstancia(EntityManager em) {
        if(instancia == null) {
            instancia = new RepositorioHeladeras(em);
        }
        return instancia;
    }

    public void agregarHeladera(Heladera heladera) {
        validarInsertHeladera(heladera);
        em.getTransaction().begin();
        em.persist(heladera);
        em.getTransaction().commit();

    }

    public void validarInsertHeladera(Heladera heladera) {
        if (heladera.getPuntoEstrategico() == null || heladera.getPuntoEstrategico().getLongitud() == null ||
                heladera.getPuntoEstrategico().getLatitud() == null || heladera.getPuntoEstrategico().getDireccion() == null) {
            throw new RuntimeException("La heladera no tiene completa la direccion");
        }
        if (heladera.getNombre() == null) {
            throw new RuntimeException("La heladera no tiene nombre");
        }
        if (heladera.getViandasMaximas() == null) {
            throw new RuntimeException("La heladera no tiene declarada la cantidad de viandas maximas");
        }
        if (heladera.getActiva() == null) {
            throw new RuntimeException("Se tiene que indicar si ya esta activa la heladera");
        }
    }

    public List<Heladera> obtenerHeladerasCercanas(Heladera heladeraAfectada, Integer cantidadHeladerasCercanas) {
        List<Heladera> heladeras = em.createQuery("SELECT h FROM Heladera h WHERE h.puntoEstrategico.areas = :area AND h.activa = TRUE", Heladera.class)
                .setParameter("area", heladeraAfectada.getPuntoEstrategico().getAreas())
                .setMaxResults(cantidadHeladerasCercanas)
                .setMaxResults(cantidadHeladerasCercanas)
                .getResultList();
        return heladeras;
    }

    public void setearInactivaHeladera(Heladera heladera) {
        em.getTransaction().begin();
        Heladera managedHeladera = em.find(Heladera.class, heladera.getId());
        managedHeladera.setActiva(false);
        em.getTransaction().commit();
    }

    public void setearActivaHeladera(Heladera heladera) {
        em.getTransaction().begin();
        Heladera managedHeladera = em.find(Heladera.class, heladera.getId());
        managedHeladera.setActiva(true);
        em.getTransaction().commit();
    }

    public void setearHabilitadaHeladera(Heladera heladera) {
        em.getTransaction().begin();
        Heladera managedHeladera = em.find(Heladera.class, heladera.getId());
        managedHeladera.setHabilitado(true);
        em.getTransaction().commit();
    }

    public void setearInhabilitadaHeladera(Heladera heladera) {
        em.getTransaction().begin();
        Heladera managedHeladera = em.find(Heladera.class, heladera.getId());
        managedHeladera.setHabilitado(false);
        em.getTransaction().commit();
    }

    public static List<Heladera> obtenerHeladeras() {
        List<Heladera> heladeras = em.createQuery("SELECT h FROM Heladera h", Heladera.class)
                .getResultList();
        return heladeras;
    }

}
