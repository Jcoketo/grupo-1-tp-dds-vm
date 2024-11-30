package persistencia;

import lombok.Getter;
import modelo.autenticacion.AuthService;
import modelo.autenticacion.Usuario;
import modelo.validador.ValidadorDeContrasenias;
import org.mindrot.jbcrypt.BCrypt;
import javax.persistence.EntityManager;


public class RepositorioUsuarios {

    private static RepositorioUsuarios instancia = null;

    private static EntityManager em;
    private static AuthService authService;

    private RepositorioUsuarios(EntityManager em) {
        this.em = em;
    }

    public static RepositorioUsuarios getInstancia(EntityManager em) {
        if(instancia == null) {
            instancia = new RepositorioUsuarios(em);
        }
        return instancia;
    }

    public void registrarUsuario(String mail, String username, String password) {
        if (existeUsuario(mail)) {
            //throw new RuntimeException("El usuario ya existe"); ----> Si el usuario no existe, debería tirar una Exception?
        }else {
            ValidadorDeContrasenias.getInstancia().validarContrasenia(username, password);
            password = authService.hashPassword(password);
            em.getTransaction().begin();
            em.persist(new Usuario(username, password));
            em.getTransaction().commit();
        }
    }

    public boolean autenticarUsuario(String mail, String password) {
        Usuario usuario = em.createQuery("SELECT u FROM Usuario u WHERE u.mail = :mail", Usuario.class)
                .setParameter("mail", mail)
                .getSingleResult();
        //em.close();

        if (usuario != null) {
            return authService.checkPassword(password, usuario.getHashedPassword());
        }
        return false;
    }
    
    public Boolean existeUsuario(String mail) {
        Usuario usuario = em.createQuery("SELECT u FROM Usuario u WHERE u.mail = :mail", Usuario.class)
                .setParameter("mail", mail)
                .getSingleResult();
        //em.close();
        return usuario != null;
    }


}
