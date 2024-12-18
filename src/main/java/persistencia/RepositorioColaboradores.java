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

    public void darDeBaja(Colaborador colaborador) {
        em.getTransaction().begin();
        em.remove(colaborador);
        em.getTransaction().commit();
    }

    public TipoPersona devolverTipoPersona(Integer idPersona) {
        TypedQuery<TipoPersona> query = em.createQuery("SELECT p.tipoPersona FROM Persona p WHERE p.id = :idPersona", TipoPersona.class);
        query.setParameter("idPersona", idPersona);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public static List<Colaborador> obtenerColaboradores() {
        return em.createQuery("SELECT c FROM Colaborador c", Colaborador.class).getResultList();
    }

    public void registrarColaboradorFisico(Usuario usuario, PersonaHumana persona, Colaborador colaborador) {
        em.getTransaction().begin();
        em.persist(usuario);
        em.persist(persona);
        em.persist(colaborador);
        em.getTransaction().commit();
    }

    public void registrarColaboradorJuridico(Usuario usuario, PersonaJuridica persona, Colaborador colaborador) {
        em.getTransaction().begin();
        em.persist(usuario);
        em.persist(persona);
        em.persist(colaborador);
        em.getTransaction().commit();
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

    public List<MedioDeContacto> obtenerMediosDeContactoXIdPersona(Integer idPersona) {
        TypedQuery<MedioDeContacto> query = em.createQuery("SELECT m FROM MedioDeContacto m WHERE m.persona.id = :idPersona", MedioDeContacto.class);
        query.setParameter("idPersona", idPersona);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Persona obtenerPersona(Integer idPersona) {
        return em.find(Persona.class, idPersona);
    }

}

