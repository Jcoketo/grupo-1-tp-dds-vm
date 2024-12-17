package persistencia;

import lombok.Getter;
import modelo.elementos.Incidente;
import modelo.excepciones.ExcepcionValidacion;
import modelo.personas.MedioDeContacto;
import modelo.personas.PersonaVulnerable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioPersonasVulnerables {
    @Getter
    private static RepositorioPersonasVulnerables instancia = null;

    private static EntityManager em;


    public RepositorioPersonasVulnerables(EntityManager entityManager) {
        this.em = entityManager;
    }

    public static RepositorioPersonasVulnerables getInstancia(EntityManager entityManager) {
        if(instancia == null) {
            instancia = new RepositorioPersonasVulnerables(entityManager);
        }
        return instancia;
    }
    public static RepositorioPersonasVulnerables getInstancia() {
        if(instancia == null) {
            throw new ExcepcionValidacion("No fue instanciado en el repositorio!");
        }
        return instancia;
    }

    public void agregarPersonaVulnerable(PersonaVulnerable PV){
        validarInsertPersonaVulnerable(PV);
        em.getTransaction().begin();
        em.persist(PV);
        em.getTransaction().commit();
    }

    public void validarInsertPersonaVulnerable(PersonaVulnerable PV){
        if (PV.getFechaRegistro() == null)
            throw new ExcepcionValidacion("La PV no tiene una fecha de registro asociada");

        if (PV.getTarjeta() == null)
            throw new ExcepcionValidacion("La PV no tiene un numero de tarjeta asociado");

        if (PV.getDioAlta() == null)
            throw new ExcepcionValidacion("La PV no tiene alguien que lo haya dado de alta");

    }

    public void eliminarPV(PersonaVulnerable PV) {
        PersonaVulnerable managedPV = em.find(PersonaVulnerable.class, PV.getId());
        if (managedPV != null) {
            em.getTransaction().begin();
            em.remove(managedPV);
            em.getTransaction().commit();
        }
    }

    public List<PersonaVulnerable> obtenerPersonasVulnerablesRegistradasPor(Integer idPersona){
        TypedQuery<PersonaVulnerable> query = em.createQuery("SELECT p FROM PersonaVulnerable p WHERE p.dioAlta = :idPersona", PersonaVulnerable.class);
        query.setParameter("idPersona", idPersona);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
