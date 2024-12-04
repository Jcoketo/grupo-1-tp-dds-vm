package persistencia;

import lombok.Getter;
import modelo.elementos.Incidente;
import modelo.elementos.Tarjeta;
import modelo.elementos.TarjetaPlastica;
import modelo.personas.Tecnico;
import pruebas.IdGenerator;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class RepositorioTarjetas {
    private static RepositorioTarjetas instancia = null;
    //private String ultimoID;

    private static EntityManager em;

    private RepositorioTarjetas(EntityManager em) {
        this.em = em;
        //this.ultimoID = "00000000000";
    }

    public static RepositorioTarjetas getInstancia(EntityManager em) {
        if(instancia == null) {
            instancia = new RepositorioTarjetas(em);
        }
        return instancia;
    }

    /*public static RepositorioTarjetas getInstancia() {
        if(instancia == null) {
            throw new RuntimeException("Repositorio de Tarjetas no creado");
        }
        return instancia;
    }

    public String generarIdTarjeta() {
        String nuevoID = IdGenerator.generateNextId();
        //this.ultimoID = nuevoID;
        return nuevoID;
    }*/

    public void agregarTarjeta(TarjetaPlastica tarjeta){
        validarInsertTarjeta(tarjeta);
        em.getTransaction().begin();
        em.persist(tarjeta);
        em.getTransaction().commit();
    }

    public void validarInsertTarjeta(TarjetaPlastica tarjeta){
        if(tarjeta.getNro_tarjeta() == null){
            throw new RuntimeException("La tarjeta no tiene numero asociado");
        }
    }

    public void eliminarTarjeta(TarjetaPlastica tarjeta) {
        em.getTransaction().begin();
        TarjetaPlastica managedTarjeta = em.find(TarjetaPlastica.class, tarjeta.getId());
        if (managedTarjeta != null) {
            em.remove(managedTarjeta);
            em.getTransaction().commit();
        }
    }


}