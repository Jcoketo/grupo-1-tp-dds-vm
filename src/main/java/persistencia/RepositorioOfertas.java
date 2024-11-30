package persistencia;

import lombok.Getter;
import modelo.colaboracion.Oferta;
import modelo.personas.Tecnico;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class RepositorioOfertas {
    @Getter
    private static RepositorioOfertas instancia = null;

    private static EntityManager em;

    private RepositorioOfertas(EntityManager em) {
        this.em = em;
    }

    public static RepositorioOfertas getInstancia(EntityManager em) {
        if(instancia == null) {
            instancia = new RepositorioOfertas(em);
        }
        return instancia;
    }

    public void agregarOferta(Oferta oferta) {
        validarInsertOferta(oferta);
        em.getTransaction().begin();
        em.persist(oferta);
        em.getTransaction().commit();
    }

    public void validarInsertOferta(Oferta oferta){
        if(oferta.getNombre() == null){
            throw new RuntimeException("La oferta no tiene un nombre asociado");
        }
        if(oferta.getTipoOferta() == null){
            throw new RuntimeException("La oferta no tiene un tipo asociado");
        }
        if(oferta.getRubro() == null){
            throw new RuntimeException("La oferta no tiene un rubro asociado");
        }
        if(oferta.getPuntosNecesarios() == null){
            throw new RuntimeException("La oferta no tiene los puntos necesarios para ser canjeado");
        }
    }

    public void darDeBaja(Oferta oferta) {
        em.getTransaction().begin();
        Oferta managedOferta = em.find(Oferta.class, oferta.getId());
        if (managedOferta != null) {
            managedOferta.setDisponibilidad(false);
            em.getTransaction().commit();
        }
    }


    public List<Oferta> conocerOfertasDisponibles() {
        List<Oferta> ofertas = em.createQuery("SELECT u FROM Oferta u WHERE u.disponibilidad = TRUE", Oferta.class)
                .getResultList();
        return ofertas;
    }
 }
