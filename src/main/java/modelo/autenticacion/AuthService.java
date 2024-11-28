package modelo.autenticacion;

import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AuthService {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    public Usuario registrarUsuario(String mail, String password) {
        EntityManager em = emf.createEntityManager();
        String hashedPassword = hashPassword(password);
        Usuario usuario = new Usuario();
        usuario.setMail(mail);
        usuario.setHashedPassword(hashedPassword);

        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();
        em.close();

        return usuario;
    }

    public boolean autenticarUsuario(String mail, String password) {
        EntityManager em = emf.createEntityManager();
        Usuario usuario = em.createQuery("SELECT u FROM Usuario u WHERE u.mail = :mail", Usuario.class)
                .setParameter("mail", mail)
                .getSingleResult();
        em.close();

        if (usuario != null) {
            return checkPassword(password, usuario.getHashedPassword());
        }
        return false;
    }
}