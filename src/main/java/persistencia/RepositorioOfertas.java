package persistencia;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import modelo.colaboracion.Oferta;
import modelo.colaboracion.TipoOferta;
import modelo.excepciones.ExcepcionValidacion;
import modelo.personas.Colaborador;
import modelo.personas.Rubro;
import modelo.personas.Tecnico;


import javax.persistence.*;
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
    public static RepositorioOfertas getInstancia() {
        if(instancia == null) {
            throw new ExcepcionValidacion("No fue instanciado en el repositorio!");
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
        TypedQuery<Oferta> query = em.createQuery("SELECT o FROM Oferta o WHERE o.disponibilidad = true ",Oferta.class);

        try{
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Oferta buscarOfertaXId(Integer idOferta) {
        TypedQuery<Oferta> query = em.createQuery("SELECT o FROM Oferta o WHERE o.id = :idOferta", Oferta.class);
        query.setParameter("idOferta", idOferta);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


 }
