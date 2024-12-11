package persistencia;

import accessManagment.Roles;
import modelo.validador.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;


public class RepositorioUsuarios {


    private static RepositorioUsuarios instancia = null;

    private static EntityManager em;

    private RepositorioUsuarios(EntityManager em) {
        this.em = em;
    }

    public static RepositorioUsuarios getInstancia(EntityManager em) {
        if(instancia == null) {
            instancia = new RepositorioUsuarios(em);
        }
        return instancia;
    }

    public static RepositorioUsuarios getInstancia() {
        if (instancia == null) {
            throw new IllegalStateException("RepositorioUsuarios no inicializado");
        }
        return instancia;
    }

    public String traerClavexUsuario(String mail) {
        Usuario usuario = em.createQuery("SELECT u FROM Usuario u WHERE u.mail = :mail", Usuario.class)
                .setParameter("mail", mail)
                .getSingleResult();
        return usuario.getHashedPassword();
    }

    public Usuario traerUsuario(String mail) {
        Usuario usuario = em.createQuery("SELECT u FROM Usuario u WHERE u.mail = :mail", Usuario.class)
                .setParameter("mail", mail)
                .getSingleResult();
        return usuario;
    }
    
    public Boolean existeMAIL(String mail) {
        TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.mail = :mail", Usuario.class);
        query.setParameter("mail", mail);
        try {
            query.getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    public void persistirUsuario(String mail, String username, String password, Roles rol) {
        em.getTransaction().begin();
        em.persist(new Usuario(mail, username, password, rol));
        em.getTransaction().commit();
    }

}
