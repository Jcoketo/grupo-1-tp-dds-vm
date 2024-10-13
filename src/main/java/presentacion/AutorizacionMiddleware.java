package presentacion;

import accessManagment.Roles;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

public class AutorizacionMiddleware implements Handler {
    @Getter @Setter private boolean debeSerAdmin = false;
    @Getter @Setter private boolean debeEstarLogueado = false;

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Roles userRole = context.sessionAttribute("rolUsuario");
        Boolean estaLogueado = context.sessionAttribute("logueado");

        if( estaLogueado == null ){ estaLogueado = false; }

        if(debeEstarLogueado && !estaLogueado){
            context.redirect("/login");
        }

        if (userRole != null && estaLogueado != null && estaLogueado) {
            if (debeSerAdmin && userRole.equals(Roles.USUARIO)) {
                context.redirect("/404");
            }
        }
    }

    public AutorizacionMiddleware setAdmin() {
        this.debeSerAdmin = true;
        return this;
    }

    public AutorizacionMiddleware setLogueado() {
        this.debeEstarLogueado = true;
        return this;
    }
}