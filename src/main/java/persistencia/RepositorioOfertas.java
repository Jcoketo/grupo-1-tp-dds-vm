package persistencia;

import modelo.colaboracion.Oferta;
import modelo.personas.Tecnico;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class RepositorioOfertas {
    private static RepositorioOfertas instancia = null;
    private static EntityManagerFactory emf;
    private static EntityManager em;

    private RepositorioOfertas() {
        emf = Persistence.createEntityManagerFactory("db");
        em = emf.createEntityManager();
    }

    public static RepositorioOfertas getInstancia() {
        if(instancia == null) {
            instancia = new RepositorioOfertas();
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

    public void eliminar(Oferta oferta) {
        em.getTransaction().begin();
        Oferta managedOferta = em.find(Oferta.class, oferta.getId());
        if (managedOferta != null) {
            em.remove(managedOferta);
            em.getTransaction().commit();
        }
    }


    /*public List<Oferta> conocerOfertasDisponibles() {
        List<Oferta> ofertas = em.createQuery("SELECT o FROM Oferta o WHERE o.disponibilidad = :true", Oferta.class)
                .setParameter("disponibilidad", disponibilidad)
                .getResultList();
        em.createNativeQuery("SELECT o FROM Oferta o WHERE Disponbilidad )
    }*/
 }
