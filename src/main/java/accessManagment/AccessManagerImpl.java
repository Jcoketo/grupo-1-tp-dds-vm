package accessManagment;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.security.AccessManager;
import io.javalin.security.RouteRole;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class AccessManagerImpl implements AccessManager {

    @Override
    public void manage(@NotNull Handler handler, @NotNull Context context, @NotNull Set<? extends RouteRole> permittedRoles) throws Exception {
        // Verifica si el usuario está logueado
        boolean isLoggedIn = Boolean.TRUE.equals(context.sessionAttribute("logueado"));

        if (permittedRoles.contains(Roles.SIN_PERMISOS)) {
            handler.handle(context);
        } else if (isLoggedIn && permittedRoles.contains(Roles.CON_PERMISOS)) {
            handler.handle(context);
        } else {
            context.status(401).result("Unauthorized");
        }
    }
}