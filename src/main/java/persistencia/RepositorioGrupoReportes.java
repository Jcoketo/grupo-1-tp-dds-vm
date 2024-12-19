package persistencia;

import lombok.Getter;
import modelo.excepciones.ExcepcionValidacion;
import modelo.reportador.GrupoReporte;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class RepositorioGrupoReportes {
    @Getter
    private static RepositorioGrupoReportes instancia = null;

    private static EntityManager em;

    private RepositorioGrupoReportes(EntityManager entityManager) {
        em = entityManager;
    }

    public static RepositorioGrupoReportes getInstancia(EntityManager entityManager) {
        if(instancia == null) {
            instancia = new RepositorioGrupoReportes(entityManager);
        }
        return instancia;
    }
    public static RepositorioGrupoReportes getInstancia() {
        if(instancia == null) {
            throw new ExcepcionValidacion("No fue instanciado en el repositorio!");
        }
        return instancia;
    }

    public void agregarReporte(GrupoReporte reporte){
        validarInsertReporte(reporte);
        em.getTransaction().begin();
        em.persist(reporte);
        em.getTransaction().commit();
    }

    public void validarInsertReporte(GrupoReporte reporte) {
        if (reporte.getReportes() == null) {
            throw new RuntimeException("El reporte no tiene un link asociado");
        }
    }

    public void eliminar(GrupoReporte reporte) {
        em.getTransaction().begin();
        GrupoReporte managedReporte = em.find(GrupoReporte.class, reporte.getId());
        if (managedReporte != null) {
            em.remove(managedReporte);
            em.getTransaction().commit();
        }
    }

    public GrupoReporte obtenerUltimoGrupoReporte() {
        TypedQuery<GrupoReporte> query = em.createQuery(
                "SELECT r FROM GrupoReporte r ORDER BY r.fechaCreacion DESC", //revisar que este bien
                GrupoReporte.class);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}