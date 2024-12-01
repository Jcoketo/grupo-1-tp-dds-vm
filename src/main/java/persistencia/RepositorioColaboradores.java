package persistencia;

import accessManagment.Roles;
import lombok.Getter;
import modelo.contrasenia.PasswordGenerator;
import modelo.notificador.Notificador;
import modelo.personas.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class RepositorioColaboradores {
    @Getter
    private static RepositorioColaboradores instancia = null;

    private static EntityManager em;

    private RepositorioColaboradores(EntityManager em) {
        this.em = em;
    }

    public static RepositorioColaboradores getInstancia(EntityManager em) {
        if(instancia == null) {
            instancia = new RepositorioColaboradores(em);
        }
        return instancia;
    }

    public PersonaHumana existePersonaFisica(String nroDoc, TipoDocumento tipoDoc) {
        TypedQuery<PersonaHumana> query = em.createQuery(
                "SELECT p FROM PersonaHumana p WHERE p.documento.numeroDoc = :numeroDoc AND p.documento.tipoDoc = :tipoDoc",
                PersonaHumana.class
        );
        query.setParameter("numeroDoc", nroDoc);
        query.setParameter("tipoDoc", tipoDoc);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void agregar(Colaborador colaborador) {
        em.getTransaction().begin();
        em.persist(colaborador);
        em.getTransaction().commit();
    }

    public void darDeBaja(Colaborador colaborador) {
        em.getTransaction().begin();
        em.remove(colaborador);
        em.getTransaction().commit();
    }

    public TipoPersona devolverTipoPersona(String email) {
        List<Persona> personas = em.createQuery("SELECT p FROM Persona p JOIN p.mediosDeContacto m WHERE m.contacto = :email", Persona.class)
                .setParameter("email", email)
                .getResultList();
        if (!personas.isEmpty()) {
            return personas.get(0).getTipoPersona();
        }
        return null;
    }

    public static List<Colaborador> obtenerColaboradores() {
        return em.createQuery("SELECT c FROM Colaborador c", Colaborador.class).getResultList();
    }

    public Roles devolverRol(String email) {
        //TODO
        // TABLA DE ROLES
        return null;
    }

    public void registrarColaboradorFisico(TipoDocumento tipoDoc, String nroDoc, String nombre, String apellido, String mail, String telefono, String direccion, String fechaNacimiento) {

        PersonaHumana persona = new PersonaHumana(tipoDoc, nroDoc, nombre, apellido, mail, telefono, direccion, fechaNacimiento);
        Colaborador colaborador = new Colaborador(persona);

        em.getTransaction().begin();
        em.persist(persona);
        em.persist(colaborador);
        em.getTransaction().commit();

        String mensajeBienvenida = "Bienvenido a la plataforma " + nombre + ". Ojala que te diviertas";

        Notificador.notificarXNuevoUsuario(mensajeBienvenida, colaborador);

    }
       // IMPORTADOR CSV
    public Colaborador crearColaboradorFisico(TipoDocumento tipoDocumento, String nroDocumento, String nombre, String apellido, String mail) {
        String identificadorUnico = tipoDocumento + nroDocumento;

            MedioDeContacto medioDeContacto = new MedioDeContacto(TipoMedioDeContacto.MAIL, mail);

            PersonaHumana persona = new PersonaHumana(tipoDocumento, nroDocumento, nombre, apellido, medioDeContacto);
            Colaborador colaborador = new Colaborador(persona);

            this.agregar(colaborador);

            String password = PasswordGenerator.generatePassword();
            // TODO ACA IRIA EL CODIGO PARA ALMACENAR EN LA BD LAS CREDENCIALES
            String mensajeMasCredenciales = "Bienvenido a la plataforma. Su usuario es: " + identificadorUnico + " y su contraseña es: " + password;

            Notificador.notificarXNuevoUsuario(mensajeMasCredenciales, colaborador);

            return colaborador;



    }

    public Colaborador existeColaborador(Integer id) {
        return em.find(Colaborador.class, id);
    }

    /*public MedioDeContacto existeMail(String mail) {
        TypedQuery<MedioDeContacto> query = em.createQuery("SELECT m FROM MedioDeContacto m WHERE m.contacto = :mail", MedioDeContacto.class);
        query.setParameter("mail", mail);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }*/

}

