package persistencia;

import lombok.Getter;
import modelo.elementos.Incidente;
import modelo.personas.PersonaVulnerable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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

    public void agregarPersonaVulnerable(PersonaVulnerable PV){
        validarInsertPersonaVulnerable(PV);
        em.getTransaction().begin();
        em.persist(PV);
        em.getTransaction().commit();
    }

    public void validarInsertPersonaVulnerable(PersonaVulnerable PV){
        if (PV.getFechaRegistro() == null) {
            throw new RuntimeException("La PV no tiene una fecha de registro asociada");
        }

        if (PV.getMenoresACargo() == 0) {
            throw new RuntimeException("La PV no tiene menores a cargo");
        }

        if (PV.getTarjeta() == null) {
            throw new RuntimeException("La PV no tiene un numero de tarjeta asociado");
        }

        if (PV.getDioAlta() == null) {
            throw new RuntimeException("La PV no tiene alguien que lo haya dado de alta");
        } //revisar, la PV tiene que tener una persona que lo haya dado de alta sí o sí

        if (PV.getPersona() == null) {
            throw new RuntimeException("La PV no tiene una persona real asociada");
        }


    }

    public void eliminarPV(PersonaVulnerable PV) {
        em.getTransaction().begin();
        PersonaVulnerable managedPV = em.find(PersonaVulnerable.class, PV.getId());
        if (managedPV != null) {
            em.remove(managedPV);
            em.getTransaction().commit();
        }
    }

}
