package persistencia;

import lombok.Getter;
import modelo.elementos.FallaTecnica;
import modelo.excepciones.ExcepcionValidacion;
import modelo.personas.Tecnico;
import modelo.personas.Visita;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class RepositorioVisitas {
    private static RepositorioVisitas instancia = null;

    private static EntityManager em;

    public static RepositorioVisitas getInstancia(EntityManager entityManager) {
        if (instancia == null) {
            instancia = new RepositorioVisitas(entityManager);
        }
        return instancia;
    }

    public static RepositorioVisitas getInstancia() {
        if(instancia == null) {
            throw new ExcepcionValidacion("No fue instanciado en el repositorio!");
        }
        return instancia;
    }

    private RepositorioVisitas(EntityManager entityManager) {
        em = entityManager;
    }

    public void agregarVisita(Visita visita) {
        try {
            this.validarInsertVisita(visita);
            em.getTransaction().begin();
            em.persist(visita);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }

    public void validarInsertVisita(Visita visita) {
        if (visita.getTecnico() == null) {
            throw new RuntimeException("La visita no tiene un técnico asociado");
        }
        if (visita.getHeladera() == null) {
            throw new RuntimeException("La visita no tiene una heladera asociada");
        }
        if (visita.getDescripcion() == null) {
            throw new RuntimeException("La visita no tiene una descripcion asociadas");
        }
    }

    public void eliminarVisita(Visita visita) {
        em.getTransaction().begin();
        Visita managedVisita = em.find(Visita.class, visita.getId());
        if (managedVisita != null) {
            em.remove(managedVisita);
            em.getTransaction().commit();
        }
    }

    public List<Visita> getVisitasPorTecnico(Integer idTecnico) {
        TypedQuery<Visita> query = em.createQuery("SELECT v FROM Visita v WHERE v.tecnico.id = :idTecnico", Visita.class);
        query.setParameter("idTecnico", idTecnico);
        try {
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}