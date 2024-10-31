package pruebas;

import modelo.elementos.Heladera;
import modelo.elementos.PuntoEstrategico;
import modelo.personas.*;
import modelo.suscripcion.SuscriptoCantidad;
import modelo.suscripcion.TipoSuscripcion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class InsertDataTest {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
        EntityManager em = emf.createEntityManager();

        //EntityManager em = entityManager();

        em.getTransaction().begin();

        /*
        // Crear y persistir una PersonaHumana
        MedioDeContacto medioDeContacto = new MedioDeContacto(TipoMedioDeContacto.MAIL, "example@example.com");
        PersonaHumana personaHumana = new PersonaHumana("John", "Doe", medioDeContacto);
        em.persist(personaHumana);
        */


        // Crear y persistir una PersonaJuridica
        MedioDeContacto medioDeContactoPJ = new MedioDeContacto(TipoMedioDeContacto.TELEFONO, "123456789");
        em.persist(medioDeContactoPJ);  // Persistir el medio de contacto antes de PersonaJuridica

        PersonaJuridica personaJuridica = new PersonaJuridica("Empresa S.A.", TipoJuridica.GUBERNAMENTAL, Rubro.ELECTRONICA, medioDeContactoPJ);
        em.persist(personaJuridica);

        PuntoEstrategico puntoEstrategico = new PuntoEstrategico(13.0, 13.0);
        em.persist(puntoEstrategico);  // Persistir PuntoEstrategico antes de asignarlo a Heladera

        Heladera heladera = new Heladera(14, LocalDate.now(), puntoEstrategico);
        em.persist(heladera);

        Colaborador colaborador = new Colaborador(personaJuridica);
        em.persist(colaborador);  // Persistir Colaborador antes de asignarlo a SuscriptoCantidad

        SuscriptoCantidad suscriptoCantidad = new SuscriptoCantidad(heladera, colaborador, TipoSuscripcion.QUEDAN_POCAS, 10, TipoMedioDeContacto.MAIL);
        em.persist(suscriptoCantidad);

        // Establecer relaciones después de persistir
        personaJuridica.getHeladeras().add(heladera);
        heladera.getColaboradoresSucriptos().add(suscriptoCantidad);






        /*
        List<Heladera> heladeras = em
                // equivalente a: select * from persona where persona.nombre = 'Julian'
                .createQuery("select h from Heladera h where h.id = 3" , Heladera.class) //ojo, query no tipada
                .getResultList();

        personaJuridica.setHeladeras(heladeras);
        */

        em.getTransaction().commit();
        em.close();
        emf.close();
    }
}
