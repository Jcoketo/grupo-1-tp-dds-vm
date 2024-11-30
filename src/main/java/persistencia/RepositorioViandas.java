package persistencia;

import lombok.Getter;
import modelo.colaboracion.Vianda;
import javax.persistence.EntityManager;

public class RepositorioViandas {
    @Getter
    private static RepositorioViandas instancia = null;

    private static EntityManager em;

    public RepositorioViandas(EntityManager entityManager ){
        em = entityManager;
    }

    public static RepositorioViandas getInstancia(EntityManager entityManager) {
        if(instancia == null) {
            instancia = new RepositorioViandas(entityManager);
        }
        return instancia;
    }
    public void agregarVianda(Vianda vianda){
        try {
            validarInsertVianda(vianda);
            em.getTransaction().begin();
            em.persist(vianda);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }

    public void validarInsertVianda(Vianda vianda) {
        if (vianda.getTipoComida() == null) {
            throw new RuntimeException("La vianda no tiene tipo de comida");
        }
        if (vianda.getFechaCaducidad() == null) {
            throw new RuntimeException("La vianda no tiene fecha de caducidad");
        }
        if (vianda.getColaborador() == null) {
            throw new RuntimeException("La vianda no tiene colaborador");
        }
        if (vianda.getDisponibleEn() == null) {
            throw new RuntimeException("La vianda no tiene heladera asignada");
        }
    }

    public void setearEntregada(Vianda vianda){
        vianda = em.find(Vianda.class, vianda.getId());
        if (vianda == null) {
            throw new RuntimeException("La vianda no existe");
        }
        vianda.setEntregada(true);
        em.getTransaction().begin();
        em.merge(vianda);
        em.getTransaction().commit();

    }



}
