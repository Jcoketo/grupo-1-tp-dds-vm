package persistencia;

import lombok.Getter;
import modelo.elementos.Incidente;
import modelo.elementos.Tarjeta;
import modelo.elementos.TarjetaPlastica;
import modelo.excepciones.ExcepcionValidacion;
import modelo.personas.Tecnico;
import pruebas.IdGenerator;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class RepositorioTarjetas {
    @Getter
    private static RepositorioTarjetas instancia;

    private String ultimoID;

    private static EntityManager em;

    private RepositorioTarjetas(EntityManager em) {
        this.em = em;
        this.ultimoID = this.buscarUltimoId();
    }

    public static RepositorioTarjetas getInstancia(EntityManager em) {
        if(instancia == null) {
            instancia = new RepositorioTarjetas(em);
        }
        return instancia;
    }

    public static RepositorioTarjetas getInstancia() {
        if(instancia == null) {
            throw new ExcepcionValidacion("No fue instanciado en el repositorio!");
        }
        return instancia;
    }

    public String buscarUltimoId() {
        String ultimoId = em.createQuery(
                        "SELECT t.nro_tarjeta FROM TarjetaPlastica t ORDER BY t.nro_tarjeta DESC", String.class)
                .setMaxResults(1) // Limita a un resultado
                .getResultStream() // Obtén un Stream para manejar posibles resultados vacíos
                .findFirst() // Toma el primer elemento si existe
                .orElse("0"); // Si no hay resultados, devuelve "0"
        return ultimoId;
    }


    public String generarIdTarjeta() {
        return IdGenerator.generateNextId(ultimoID);
    }

    public void agregarTarjeta(TarjetaPlastica tarjeta){
        validarInsertTarjeta(tarjeta);
        em.getTransaction().begin();
        em.persist(tarjeta);
        em.getTransaction().commit();
    }

    public void validarInsertTarjeta(TarjetaPlastica tarjeta){
        if(tarjeta.getNro_tarjeta() == null){
            throw new ExcepcionValidacion("La tarjeta no tiene numero asociado");
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

    public List<TarjetaPlastica> crearNTarjetasPlasticas(Integer N) {
        List<TarjetaPlastica> tarjetasPlasticas = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            String nuevoId = this.generarIdTarjeta();
            TarjetaPlastica tarjeta = new TarjetaPlastica(nuevoId);
            this.agregarTarjeta(tarjeta);
            this.ultimoID = nuevoId;
            tarjetasPlasticas.add(tarjeta);
        }

        return tarjetasPlasticas;
    }

}