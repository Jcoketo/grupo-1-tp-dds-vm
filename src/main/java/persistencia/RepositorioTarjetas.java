package persistencia;

import lombok.Getter;
import modelo.elementos.Incidente;
import modelo.elementos.Tarjeta;
import pruebas.IdGenerator;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class RepositorioTarjetas {
    @Getter
    private static RepositorioTarjetas instancia = null;
    private String ultimoID;

    private static EntityManager em;

    private RepositorioTarjetas(EntityManager em) {
        this.em = em;
        this.ultimoID = "00000000000";
    }

    public static RepositorioTarjetas getInstancia(EntityManager em) {
        if(instancia == null) {
            instancia = new RepositorioTarjetas(em);
        }
        return instancia;
    }

    public static RepositorioTarjetas getInstancia() {
        if(instancia == null) {
            throw new RuntimeException("Repositorio de Tarjetas no creado");
        }
        return instancia;
    }

    public String generarIdTarjeta() {
        String nuevoID = IdGenerator.generateNextId(this.ultimoID);
        this.ultimoID = nuevoID;
        return nuevoID;
    }

    public void agregarTarjeta(Tarjeta tarjeta){
        validarInsertTarjeta(tarjeta);
        em.getTransaction().begin();
        em.persist(tarjeta);
        em.getTransaction().commit();
    }

    public void validarInsertTarjeta(Tarjeta tarjeta){



    }


}
