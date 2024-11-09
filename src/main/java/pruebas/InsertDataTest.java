package pruebas;

import modelo.colaboracion.Oferta;
import modelo.colaboracion.TipoOferta;
import modelo.elementos.*;
import modelo.personas.*;
import modelo.suscripcion.SuscriptoCantidad;
import modelo.suscripcion.TipoSuscripcion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class InsertDataTest {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        // Insert Documento
        Documento doc1 = new Documento("42148665", TipoDocumento.DNI);
        Documento doc2 = new Documento("12345678", TipoDocumento.LC);

        // Insert PersonaHumana
        MedioDeContacto medioDeContacto1 = new MedioDeContacto(TipoMedioDeContacto.MAIL, "example1@example.com");
        MedioDeContacto medioDeContacto2 = new MedioDeContacto(TipoMedioDeContacto.TELEFONO, "987654321");
        PersonaHumana personaHumana1 = new PersonaHumana("John", "Doe", medioDeContacto1);
        PersonaHumana personaHumana2 = new PersonaHumana("Jane", "Smith", medioDeContacto2);
        personaHumana1.setDocumento(doc1);
        personaHumana2.setDocumento(doc2);
        em.persist(personaHumana1);
        em.persist(personaHumana2);

        // Insert PersonaJuridica
        MedioDeContacto medioDeContactoPJ1 = new MedioDeContacto(TipoMedioDeContacto.TELEFONO, "123456789");
        MedioDeContacto medioDeContactoPJ2 = new MedioDeContacto(TipoMedioDeContacto.MAIL, "contact@empresa.com");
        em.persist(medioDeContactoPJ1);
        em.persist(medioDeContactoPJ2);

        PersonaJuridica personaJuridica1 = new PersonaJuridica("Empresa S.A.", TipoJuridica.GUBERNAMENTAL, Rubro.ELECTRONICA, medioDeContactoPJ1);
        PersonaJuridica personaJuridica2 = new PersonaJuridica("Tech Corp.", TipoJuridica.ONG, Rubro.ELECTRONICA, medioDeContactoPJ2);
        em.persist(personaJuridica1);
        em.persist(personaJuridica2);

        // Insert PuntoEstrategico
        PuntoEstrategico puntoEstrategico1 = new PuntoEstrategico(13.0, 13.0);
        PuntoEstrategico puntoEstrategico2 = new PuntoEstrategico(14.0, 14.0);
        em.persist(puntoEstrategico1);
        em.persist(puntoEstrategico2);

        // Insert Heladera
        Heladera heladera1 = new Heladera(14, LocalDate.now(), puntoEstrategico1);
        Heladera heladera2 = new Heladera(20, LocalDate.now(), puntoEstrategico2);
        Heladera heladera3 = new Heladera(25, LocalDate.now(), puntoEstrategico1);
        em.persist(heladera1);
        em.persist(heladera2);
        em.persist(heladera3);

        // Insert Colaborador
        Colaborador colaborador1 = new Colaborador(personaJuridica1);
        Colaborador colaborador2 = new Colaborador(personaJuridica2);
        em.persist(colaborador1);
        em.persist(colaborador2);

        // Insert SuscriptoCantidad
        SuscriptoCantidad suscriptoCantidad1 = new SuscriptoCantidad(heladera1, colaborador1, TipoSuscripcion.QUEDAN_POCAS, 10, TipoMedioDeContacto.MAIL);
        SuscriptoCantidad suscriptoCantidad2 = new SuscriptoCantidad(heladera2, colaborador2, TipoSuscripcion.QUEDAN_POCAS, 5, TipoMedioDeContacto.TELEFONO);
        em.persist(suscriptoCantidad1);
        em.persist(suscriptoCantidad2);

        // Insert Alerta
        Alerta alerta1 = new Alerta(TipoAlerta.FALLA_EN_CONEXION, heladera1);
        Alerta alerta2 = new Alerta(TipoAlerta.FALLA_TEMPERATURA, heladera1);
        em.persist(alerta1);
        em.persist(alerta2);

        // Insert FallaTecnica
        FallaTecnica fallaTecnica1 = new FallaTecnica(heladera1, colaborador1, "Descripción de la falla técnica 1", "ubicacion fisica del archivo");
        FallaTecnica fallaTecnica2 = new FallaTecnica(heladera2, colaborador2, "Descripción de la falla técnica 2", "ubicacion fisica del archivo");
        em.persist(fallaTecnica1);
        em.persist(fallaTecnica2);

        // Insert Oferta
        Oferta oferta1 = new Oferta("Un helado", "helado de pistacho", TipoOferta.PRODUCTO, Rubro.GASTRONOMIA, 10.0, "imagenHelado.jpg");
        Oferta oferta2 = new Oferta("Una milanga", "mila a caballo", TipoOferta.PRODUCTO, Rubro.GASTRONOMIA, 15.0, "imagenMila.jpg");
        em.persist(oferta1);
        em.persist(oferta2);

        // Establish relationships
        personaJuridica1.getHeladeras().add(heladera1);
        personaJuridica2.getHeladeras().add(heladera2);
        heladera1.getColaboradoresSucriptos().add(suscriptoCantidad1);
        heladera2.getColaboradoresSucriptos().add(suscriptoCantidad2);

        em.getTransaction().commit();
        em.close();
        emf.close();
    }
}