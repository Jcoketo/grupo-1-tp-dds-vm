package modelo.authService;

import modelo.excepciones.ExcepcionValidacion;
import modelo.validador.ValidadorDeContrasenias;
import org.mindrot.jbcrypt.BCrypt;
import persistencia.RepositorioUsuarios;

public class AuthServiceUsuario {

    private static RepositorioUsuarios repoUsuarios = RepositorioUsuarios.getInstancia();

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    public static void registrarUsuario(String mail, String username, String password) {    // Puede existir varios Usuarios con el mismo username.
        if (repoUsuarios.existeMAIL(mail)) {
            throw new ExcepcionValidacion("El usuario ya existe");
        }
        Boolean esValida = ValidadorDeContrasenias.getInstancia().validarContrasenia(username, password);
        if (!esValida) {
            throw new ExcepcionValidacion("La contraseña no es válida");
        }
        password = hashPassword(password);
        repoUsuarios.registrarUsuario(mail, username, password);
    }

    public static boolean autenticarUsuario(String mail, String password) {
        if (!repoUsuarios.existeMAIL(mail)) {
            throw new ExcepcionValidacion("El usuario no existe");
        }
        String passwordBase = repoUsuarios.traerClavexUsuario(mail);
        return checkPassword(password, passwordBase);
    }

}