package pruebas;

import modelo.colaboracion.Oferta;
import modelo.colaboracion.TipoOferta;
import modelo.elementos.*;
import modelo.personas.*;
import modelo.suscripcion.SuscripcionXCantidad;
import modelo.suscripcion.TipoSuscripcion;
import persistencia.RepositorioColaboradores;
import persistencia.RepositorioHeladeras;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class InsertDataTest {
    public static void main(String[] args) {

        /*
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

        PersonaJuridica personaJuridica1 = new PersonaJuridica("Empresa S.A.", TipoJuridico.GUBERNAMENTAL, Rubro.ELECTRONICA, medioDeContactoPJ1);
        PersonaJuridica personaJuridica2 = new PersonaJuridica("Tech Corp.", TipoJuridico.ONG, Rubro.ELECTRONICA, medioDeContactoPJ2);
        em.persist(personaJuridica1);
        em.persist(personaJuridica2);

        // Insert PuntoEstrategico
        PuntoEstrategico puntoEstrategico1 = new PuntoEstrategico(13.0, 13.0);
        PuntoEstrategico puntoEstrategico2 = new PuntoEstrategico(14.0, 14.0);
        em.persist(puntoEstrategico1);
        em.persist(puntoEstrategico2);

        // Insert Heladera
        Heladera heladera1 = new Heladera(14, puntoEstrategico1);
        Heladera heladera2 = new Heladera(20, puntoEstrategico2);
        Heladera heladera3 = new Heladera(25, puntoEstrategico1);
        em.persist(heladera1);
        em.persist(heladera2);
        em.persist(heladera3);

        // Insert Colaborador
        Colaborador colaborador1 = new Colaborador(personaJuridica1);
        Colaborador colaborador2 = new Colaborador(personaJuridica2);
        em.persist(colaborador1);
        em.persist(colaborador2);

        // Insert SuscriptoCantidad
        SuscripcionXCantidad suscripcionXCantidad1 = new SuscripcionXCantidad(heladera1, colaborador1, TipoSuscripcion.QUEDAN_POCAS, 10, TipoMedioDeContacto.MAIL);
        SuscripcionXCantidad suscripcionXCantidad2 = new SuscripcionXCantidad(heladera2, colaborador2, TipoSuscripcion.QUEDAN_POCAS, 5, TipoMedioDeContacto.TELEFONO);
        em.persist(suscripcionXCantidad1);
        em.persist(suscripcionXCantidad2);

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
        Oferta oferta1 = new Oferta("Un helado", "helado de pistacho", TipoOferta.PRODUCTO, Rubro.GASTRONOMIA, true,10.0, "imagenHelado.jpg");
        Oferta oferta2 = new Oferta("Una milanga", "mila a caballo", TipoOferta.PRODUCTO, Rubro.GASTRONOMIA,true, 15.0, "imagenMila.jpg");
        em.persist(oferta1);
        em.persist(oferta2);

        // Establish relationships
        personaJuridica1.getHeladeras().add(heladera1);
        personaJuridica2.getHeladeras().add(heladera2);
        heladera1.getColaboradoresSucriptos().add(suscripcionXCantidad1);
        heladera2.getColaboradoresSucriptos().add(suscripcionXCantidad2);

        em.getTransaction().commit();
        em.close();
        emf.close();
    }*/

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");

        RepositorioHeladeras repositorioHeladeras = RepositorioHeladeras.getInstancia(emf.createEntityManager());
        // Insert PuntoEstrategico
        PuntoEstrategico puntoEstrategico1 = new PuntoEstrategico(13.0, 13.0);
        PuntoEstrategico puntoEstrategico2 = new PuntoEstrategico(15.0, 16.0);
        puntoEstrategico1.setDireccion("Calle Falsa 123");
        puntoEstrategico2.setDireccion("Calle Falsa 456");
        repositorioHeladeras.getEntityManager().persist(puntoEstrategico1);
        repositorioHeladeras.getEntityManager().persist(puntoEstrategico2);
        //repositorioHeladeras.em.persist(puntoEstrategico1);
        //repositorioHeladeras.em.persist(puntoEstrategico2);
        // Insert Heladera
        Heladera heladera1 = new Heladera(14, puntoEstrategico1);
        Heladera heladera2 = new Heladera(20, puntoEstrategico2);
        Heladera heladera3 = new Heladera(25, puntoEstrategico1);
        heladera1.setNombre("Heladera 1");
        heladera2.setNombre("Heladera 2");
        heladera3.setNombre("Heladera 3");
        heladera1.setViandasMaximas(5);
        heladera2.setViandasMaximas(10);
        heladera3.setViandasMaximas(15);
        repositorioHeladeras.agregarHeladera(heladera1);
        repositorioHeladeras.agregarHeladera(heladera2);
        repositorioHeladeras.agregarHeladera(heladera3);
}
}