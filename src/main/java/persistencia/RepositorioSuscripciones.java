package persistencia;

import lombok.Getter;
import modelo.excepciones.ExcepcionValidacion;
import modelo.suscripcion.Suscripcion;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class RepositorioSuscripciones {
    @Getter
    private static RepositorioSuscripciones instancia = null;

    private static EntityManager em;


    public RepositorioSuscripciones(EntityManager entityManager) {
        this.em = entityManager;
    }

    public static RepositorioSuscripciones getInstancia(EntityManager entityManager) {
        if(instancia == null) {
            instancia = new RepositorioSuscripciones(entityManager);
        }
        return instancia;
    }
    public static RepositorioSuscripciones getInstancia() {
        if(instancia == null) {
            throw new ExcepcionValidacion("No fue instanciado en el repositorio!");
        }
        return instancia;
    }

    public List<Suscripcion> obtenerSuscripciones(Integer colab){
        TypedQuery<Suscripcion> query = em.createQuery(
                "SELECT s FROM Suscripcion s WHERE s.colaborador.id = :colab",
                Suscripcion.class
        );
        query.setParameter("colab", colab);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Suscripcion buscarSuscripcionXId(Integer idSuscripcion){
        TypedQuery<Suscripcion> query = em.createQuery(
                "SELECT s FROM Suscripcion s WHERE s.id = :idSuscripcion",
                Suscripcion.class
        );
        query.setParameter("idSuscripcion", idSuscripcion);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void actualizarSuscripcion(Suscripcion suscripcion){
        em.getTransaction().begin();
        em.merge(suscripcion);
        em.getTransaction().commit();
    }


}
