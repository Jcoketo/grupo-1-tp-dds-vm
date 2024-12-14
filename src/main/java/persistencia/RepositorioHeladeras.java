package persistencia;

import lombok.Getter;
import modelo.colaboracion.Vianda;
import modelo.elementos.Heladera;
import modelo.excepciones.ExcepcionValidacion;
import modelo.suscripcion.Suscripcion;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class RepositorioHeladeras {
    private static RepositorioHeladeras instancia = null;

    @Getter
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

    public static RepositorioHeladeras getInstancia() {
        if(instancia == null) {
            throw new ExcepcionValidacion("No fue instanciado en el repositorio!");
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

    public List<Heladera> obtenerHeladerasPropias(Integer personaJuridica) {
        List<Heladera> heladeras = em.createNativeQuery(
                        "SELECT * FROM heladera WHERE persona_juridica_id = :personaJuridica", Heladera.class)
                .setParameter("personaJuridica", personaJuridica)
                .getResultList();
        return heladeras;
    }

    public void actualizarHeladera(Heladera heladera) {
        em.getTransaction().begin();
        em.persist(heladera);
        em.getTransaction().commit();
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

    public List<Heladera> obtenerTodasLasHeladeras() {
        List<Heladera> heladeras = em.createQuery("SELECT h FROM Heladera h", Heladera.class)
                .getResultList();
        return heladeras;
    }

    public List<Heladera> obtenerHeladeras() {
        TypedQuery<Heladera> query = em.createQuery("SELECT h FROM Heladera h", Heladera.class);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<Heladera>();
        }
    }

    public Heladera buscarHeladera(Integer id) {
        TypedQuery<Heladera> query = em.createQuery("SELECT c FROM Heladera c WHERE c.id = :id", Heladera.class);
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Vianda> obtenerViandasDeHeladera(Heladera origen, Integer cantidadViandas) {
        Integer id = origen.getId();
        List<Vianda> viandas = em.createQuery("SELECT v FROM Vianda v WHERE v.disponibleEn = :origen", Vianda.class)
                .setParameter("origen", id)
                .setMaxResults(cantidadViandas)
                .getResultList();
        return viandas;
    }

    public Boolean existeHeladera(Integer id) {
        Heladera heladera = em.find(Heladera.class, id);
        return heladera != null;
    }

    public void persistirSuscripcion(Suscripcion suscripcion) {
        em.getTransaction().begin();
        em.persist(suscripcion);
        em.getTransaction().commit();
    }

}
