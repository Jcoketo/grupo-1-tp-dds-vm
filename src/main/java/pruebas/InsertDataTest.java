package pruebas;

import modelo.personas.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class InsertDataTest {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
        EntityManager em = emf.createEntityManager();

        //EntityManager em = entityManager();

        em.getTransaction().begin();

        // Crear y persistir una PersonaHumana
        MedioDeContacto medioDeContacto = new MedioDeContacto(TipoMedioDeContacto.MAIL, "example@example.com");
        PersonaHumana personaHumana = new PersonaHumana("John", "Doe", medioDeContacto);
        em.persist(personaHumana);
/*
        // Crear y persistir una PersonaJuridica
        MedioDeContacto medioDeContactoPJ = new MedioDeContacto(TipoMedioDeContacto.TELEFONO, "123456789");
        PersonaJuridica personaJuridica = new PersonaJuridica("Empresa S.A.", TipoJuridica.GUBERNAMENTAL, Rubro.ELECTRONICA, medioDeContactoPJ);
        em.persist(personaJuridica);
*/
        em.getTransaction().commit();
        em.close();
        emf.close();
    }
}
