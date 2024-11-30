package persistencia;

import lombok.Getter;
import modelo.personas.Visita;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class RepositorioVisitas {
    @Getter
    private static RepositorioVisitas instancia = null;

    private static EntityManager em;

    public static RepositorioVisitas getInstancia(EntityManager entityManager) {
        if (instancia == null) {
            instancia = new RepositorioVisitas(entityManager);
        }
        return instancia;
    }

    private RepositorioVisitas(EntityManager entityManager) {
        em = entityManager;
    }

    public void agregar(Visita visita) {
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


}
