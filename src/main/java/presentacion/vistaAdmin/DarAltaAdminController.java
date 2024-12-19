package presentacion.vistaAdmin;

import accessManagment.Roles;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.validador.Usuario;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioUsuarios;

public class DarAltaAdminController implements Handler {

    private static RepositorioUsuarios repoUsuarios = RepositorioUsuarios.getInstancia();

    @Override
    public void handle(@NotNull Context context) throws Exception {

        Roles rol = context.sessionAttribute("rolUsuario");
        String mailAdmin = context.sessionAttribute("mailUsuario");

        if (rol != Roles.ADMIN) {
            context.redirect("/inicio");
            return;
        }

        Usuario user = repoUsuarios.traerUsuario(mailAdmin);
        if (user.getRol() != Roles.ADMIN) {
            context.redirect("/inicio");
            return;
        }

        String mailNuevoAdmin = context.formParam("mailNuevoAdmin");

        if ( !mailNuevoAdmin.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$") )  {
            context.sessionAttribute("notificacionAdminMail", "El mail ingresado no es valido!");
            context.redirect("/inicioAdmin");
            return;
        }

        Usuario userAux = repoUsuarios.traerUsuario(mailNuevoAdmin);
        if (userAux == null) {
            context.sessionAttribute("notificacionAdminMail", "El mail ingresado no se encuentra registrado en la base!");
            context.redirect("/inicioAdmin");
            return;
        }

        userAux.setRol(Roles.ADMIN);
        repoUsuarios.persistirUsuario(userAux);

        context.sessionAttribute("notificacionAdminMail", "El usuario " + mailNuevoAdmin + " ha sido dado de alta como administrador!");
        context.redirect("/inicioAdmin");

    }
}
