package persistencia;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import modelo.elementos.Incidente;
import modelo.excepciones.ExcepcionValidacion;
import modelo.reportador.Reporte;

import javax.persistence.EntityManager;

public class RepositorioReportes {
    @Getter
    private static RepositorioReportes instancia = null;

    private static EntityManager em;

    private RepositorioReportes(EntityManager entityManager) {
        em = entityManager;
    }

    public static RepositorioReportes getInstancia(EntityManager entityManager) {
        if(instancia == null) {
            instancia = new RepositorioReportes(entityManager);
        }
        return instancia;
    }
    public static RepositorioReportes getInstancia() {
        if(instancia == null) {
            throw new ExcepcionValidacion("No fue instanciado en el repositorio!");
        }
        return instancia;
    }

    public void agregarReporte(Reporte reporte){
        validarInsertReporte(reporte);
        em.getTransaction().begin();
        em.persist(reporte);
        em.getTransaction().commit();
    }

    public void validarInsertReporte(Reporte reporte) {
        if (reporte.getPath() == null) {
            throw new RuntimeException("El reporte no tiene un link asociado");
        }
    }

    public void eliminar(Reporte reporte) {
        em.getTransaction().begin();
        Reporte managedReporte = em.find(Reporte.class, reporte.getId());
        if (managedReporte != null) {
            em.remove(managedReporte);
            em.getTransaction().commit();
        }
    }
}