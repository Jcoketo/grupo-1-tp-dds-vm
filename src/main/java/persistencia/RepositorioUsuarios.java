package persistencia;

import lombok.Getter;
import modelo.autenticacion.AuthService;
import modelo.autenticacion.Usuario;
import org.mindrot.jbcrypt.BCrypt;
import javax.persistence.EntityManager;


public class RepositorioUsuarios {
    @Getter
    private static RepositorioUsuarios instancia = null;

    private static EntityManager em;
    private static AuthService authService;

    private RepositorioUsuarios(EntityManager em, AuthService authService) {
        this.em = em;
        this.authService = authService;
    }
    public static RepositorioUsuarios getInstancia(EntityManager em, AuthService authService) {
        if(instancia == null) {
            instancia = new RepositorioUsuarios(em, authService);

        }
        return instancia;
    }

    public void registrarUsuario(String mail, String password) {
        if (existeUsuario(mail)) {
            throw new RuntimeException("El usuario ya existe");
        }else {
            // TODO implementar que verifique que tenga ciertas reglas la clave
            password = authService.hashPassword(password);
            em.getTransaction().begin();
            em.persist(new Usuario(mail, password));
            em.getTransaction().commit();
        }
    }

    public boolean autenticarUsuario(String mail, String password) {
        Usuario usuario = em.createQuery("SELECT u FROM Usuario u WHERE u.mail = :mail", Usuario.class)
                .setParameter("mail", mail)
                .getSingleResult();
        em.close();

        if (usuario != null) {
            return authService.checkPassword(password, usuario.getHashedPassword());
        }
        return false;
    }
    
    public Boolean existeUsuario(String mail) {
        Usuario usuario = em.createQuery("SELECT u FROM Usuario u WHERE u.mail = :mail", Usuario.class)
                .setParameter("mail", mail)
                .getSingleResult();
        em.close();
        return usuario != null;
    }


}
