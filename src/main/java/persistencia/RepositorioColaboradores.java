package persistencia;

import accessManagment.Roles;
import lombok.Getter;
import modelo.colaboracion.Colaboracion;
import modelo.colaboracion.RegistroPersonasSituVulnerable;
import modelo.colaboracion.Vianda;
import modelo.excepciones.ExcepcionValidacion;
import modelo.notificador.Notificador;
import modelo.personas.*;
import modelo.validador.Usuario;

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

    public static RepositorioColaboradores getInstancia() {
        if(instancia == null) {
            throw new ExcepcionValidacion("No fue instanciado en el repositorio!");
        }
        return instancia;
    }

//    public static RepositorioColaboradores getInstance(){
//        if(instancia == null){
//            throw new ExcepcionValidacion("No fue instanciado en el repositorio!");
//        }
//        return instancia;
//    }

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

    public PersonaJuridica existePersonaJuridica(String cuit) {
        TypedQuery<PersonaJuridica> query = em.createQuery(
                "SELECT p FROM PersonaJuridica p WHERE p.CUIT = :nroCuit ", //revisar que este bien
                PersonaJuridica.class
        );
        query.setParameter("nroCuit", cuit);
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

    public void registrarColaboradorFisico(Usuario usuario, TipoDocumento tipoDoc, String nroDoc, String nombre, String apellido, String mail, String telefono, String direccion, String fechaNacimiento) {

        MedioDeContacto medioContactoMail = new MedioDeContacto(TipoMedioDeContacto.MAIL, mail);
        PersonaHumana persona = new PersonaHumana(tipoDoc, nroDoc, nombre, apellido, medioContactoMail, direccion, fechaNacimiento);
        if(!telefono.equals("")){
            MedioDeContacto medioContactoTelefono = new MedioDeContacto(TipoMedioDeContacto.TELEFONO, telefono);
            persona.agregarMediosDeContacto(medioContactoTelefono);
        }
        Colaborador colaborador = new Colaborador(persona);
        em.getTransaction().begin();
        em.persist(usuario);
        em.persist(persona);
        em.persist(colaborador);
        em.getTransaction().commit();

        String mensajeBienvenida = "Bienvenido a la plataforma " + nombre + ". Ojala que te diviertas";

        Notificador.notificarXNuevoUsuario(mensajeBienvenida, colaborador);

    }

    public void registrarColaboradorJuridico(Usuario usuario, String razonSocial, TipoJuridico tipoJuridico, Rubro rubro, String cuit, String telefono, String email) {

        MedioDeContacto medioContactoMail = new MedioDeContacto(TipoMedioDeContacto.MAIL, email);

        PersonaJuridica persona = new PersonaJuridica(cuit, razonSocial, tipoJuridico, rubro, medioContactoMail);

        if(!telefono.equals("")){
            MedioDeContacto medioContactoTelefono = new MedioDeContacto(TipoMedioDeContacto.TELEFONO, telefono);
            persona.agregarMediosDeContacto(medioContactoTelefono);
        }

        Colaborador colaborador = new Colaborador(persona);

        em.getTransaction().begin();
        em.persist(usuario);
        em.persist(persona);
        em.persist(colaborador);
        em.getTransaction().commit();
        // TODO verificar que no vuelva con NULL - todos los commits

        String mensajeBienvenida = "Bienvenido a la plataforma " + razonSocial + ". Ojala que te diviertas";

        Notificador.notificarXNuevoUsuario(mensajeBienvenida, colaborador);

    }

    public Colaborador existeColaborador(Integer id) {
        return em.find(Colaborador.class, id);
    }

    public Colaborador buscarColaboradorXIdPersona(Integer idPersona) {
        TypedQuery<Colaborador> query = em.createQuery("SELECT c FROM Colaborador c WHERE c.persona.id = :idPersona", Colaborador.class);
        query.setParameter("idPersona", idPersona);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public PersonaHumana buscarPersonaPorId(Integer idPersona) {
        return em.find(PersonaHumana.class, idPersona);
    }

    public Integer devolverIdPersona(String email) {
        List<Persona> personas = em.createQuery("SELECT p FROM Persona p JOIN p.mediosDeContacto m WHERE m.contacto = :email", Persona.class)
                .setParameter("email", email)
                .getResultList();
        if (!personas.isEmpty()) {
            return personas.get(0).getId();
        }
        return null;
    }

    public void nuevaColaboracion(Colaborador colab, Colaboracion colaboracion) {
        em.getTransaction().begin();
        em.persist(colaboracion);
        em.persist(colab);
        em.getTransaction().commit();
    }

    public void persistirColaboracion(Colaboracion colaboracion) {
        em.getTransaction().begin();
        em.persist(colaboracion);
        em.getTransaction().commit();
    }

    public void persistirColaboraciones(List<Colaboracion> colaboraciones) {
        em.getTransaction().begin();
        colaboraciones.forEach(colaboracion -> em.persist(colaboracion));
        em.getTransaction().commit();
    }

    public List<Colaboracion> getColaboraciones(Integer idPersona) {
        Colaborador colab = this.buscarColaboradorXIdPersona(idPersona);
        return colab.getColaboracionesRealizadas();
    }

    public void persistirViandas(List<Vianda> viandas) {
        em.getTransaction().begin();
        viandas.forEach(vianda -> em.persist(vianda));
        em.getTransaction().commit();
    }

    public PersonaJuridica traerPersonaPorIdJuridica(Integer idPersona) {
        return em.find(PersonaJuridica.class, idPersona);
    }
    public PersonaHumana traerPersonaPorIdFisica(Integer idPersona) {
        return em.find(PersonaHumana.class, idPersona);
    }

    public RegistroPersonasSituVulnerable traerColaboradoresXColaboradorPersonaSitu(Colaborador colab) {
        List<Integer> idsColaboraciones = colab.getColaboracionesRealizadas().stream().map(Colaboracion::getId).toList();
        TypedQuery<RegistroPersonasSituVulnerable> query = em.createQuery("SELECT c FROM RegistroPersonasSituVulnerable c WHERE c.id IN :colaboraciones ORDER BY c.id DESC", RegistroPersonasSituVulnerable.class);
        query.setParameter("colaboraciones", idsColaboraciones).setMaxResults(1);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


    public void actualizarPersona(Persona persona) {
        em.getTransaction().begin();
        em.persist(persona);
        em.getTransaction().commit();
    }

    public void actualizarColaborador(Colaborador colaborador) {
        em.getTransaction().begin();
        em.persist(colaborador);
        em.getTransaction().commit();
    }

    public Integer devolverIdPersona(TipoDocumento tipoDoc, String nroDoc) {
        TypedQuery<PersonaHumana> query = em.createQuery("SELECT p FROM PersonaHumana p WHERE p.documento.numeroDoc = :nroDoc AND p.documento.tipoDoc = :tipoDoc", PersonaHumana.class);
        query.setParameter("nroDoc", nroDoc);
        query.setParameter("tipoDoc", tipoDoc);
        try {
            return query.getSingleResult().getId();
        } catch (NoResultException e) {
            return null;
        }
    }

//    public Colaborador devolverColaborador(String email) {
//        List<Colaborador> colabs = em.createQuery("SELECT c FROM Colaborador c JOIN p.mediosDeContacto m WHERE m.contacto = :email", Colaborador.class)
//                .setParameter("email", email)
//                .getResultList();
//        if (!colabs.isEmpty()) {
//            return colabs.get(0).getColaborador();
//        }
//        return null;
//
//    }

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

