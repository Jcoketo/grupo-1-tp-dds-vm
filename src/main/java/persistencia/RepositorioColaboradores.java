package persistencia;

import accessManagment.Roles;
import lombok.Getter;
import modelo.contraseña.PasswordGenerator;
import modelo.notificador.Notificador;
import modelo.personas.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public Colaborador existeColaborador(Integer id) {
        return em.find(Colaborador.class, id);
    }

    public void agregar(Colaborador colaborador) {
        validarInsertColaborador(colaborador);
        em.getTransaction().begin();
        em.persist(colaborador);
        em.getTransaction().commit();
    }

    private void validarInsertColaborador(Colaborador colaborador) {


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

    public Colaborador crearColaboradorFisico(TipoDocumento tipoDocumento, String nroDocumento, String nombre, String apellido, String mail) {
        String identificadorUnico = tipoDocumento + nroDocumento;

        Boolean existeMail = existeMail(mail);

        if (!existeMail) { // NO EXISTE MAIL
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
        return null; //TODO
    }

    private Boolean existeMail(String mail) {
        List<MedioDeContacto> medioDeContactos = em.createQuery("SELECT m FROM MedioDeContacto m WHERE m.contacto = :mail", MedioDeContacto.class)
                .setParameter("mail", mail)
                .getResultList();
        return !medioDeContactos.isEmpty();
    }

}

