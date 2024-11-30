package persistencia;

import modelo.elementos.Incidente;
import modelo.elementos.Tarjeta;
import pruebas.IdGenerator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class RepositorioTarjetas {
    private static RepositorioTarjetas instancia = null;
    private String ultimoID;
    private static EntityManagerFactory emf;
    private static EntityManager em;

    public RepositorioTarjetas() {
        emf = Persistence.createEntityManagerFactory("db");
        em = emf.createEntityManager();
        this.ultimoID = "00000000000";
    }

    public static RepositorioTarjetas getInstancia() {
        if(instancia == null) {
            instancia = new RepositorioTarjetas();
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
