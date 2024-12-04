package persistencia;

import lombok.Getter;
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

    public void registrarUsuario(String mail, String username, String password) {
            em.getTransaction().begin();
            em.persist(new Usuario(mail, username, password));
            em.getTransaction().commit();
    }

    public String traerClavexUsuario(String mail) {
        Usuario usuario = em.createQuery("SELECT u FROM Usuario u WHERE u.mail = :mail", Usuario.class)
                .setParameter("mail", mail)
                .getSingleResult();
        return usuario.getHashedPassword();
    }

    public String traerNombreUsuario(String mail) {
        Usuario usuario = em.createQuery("SELECT u FROM Usuario u WHERE u.mail = :mail", Usuario.class)
                .setParameter("mail", mail)
                .getSingleResult();
        return usuario.getUsername();
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

    /* PUEDE DEVOLVER NULL LA CONSULTA ENTONCES ROMPE PORQUE NO LA ESTABAMOS MANEJANDO
    public Boolean existeUsuario(String mail) {
        Usuario usuario = em.createQuery("SELECT u FROM Usuario u WHERE u.mail = :mail", Usuario.class)
                .setParameter("mail", mail)
                .getSingleResult();
        //em.close();
        return usuario != null;
    }
     */
}
