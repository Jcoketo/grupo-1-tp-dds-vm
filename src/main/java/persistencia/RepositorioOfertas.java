package persistencia;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import modelo.colaboracion.Oferta;
import modelo.colaboracion.TipoOferta;
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
        List<Object> ofertas = em.createNativeQuery("SELECT nombre, descripcion,puntosnecesarios,imagen,tipoOferta FROM oferta WHERE disponibilidad = true").getResultList();

        List<Oferta> ofertaList = new ArrayList<>();

        for (Object o : ofertas) {
            Object[] datos = (Object[]) o;

            if (datos[4].equals("PRODUCTO")) {
                Oferta of = new Oferta((String) datos[0], (String) datos[1], TipoOferta.PRODUCTO, null, true, (Double) datos[2], (String) datos[3]);
                ofertaList.add(of);
            } else {
                Oferta of = new Oferta((String) datos[0], (String) datos[1], TipoOferta.SERVICIO, null, true, (Double) datos[2], (String) datos[3]);
                ofertaList.add(of);
            }
        }

        return ofertaList;
    }


 }
