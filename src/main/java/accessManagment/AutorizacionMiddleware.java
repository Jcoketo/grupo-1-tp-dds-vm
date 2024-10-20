package accessManagment;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.Getter;
import lombok.Setter;
import modelo.personas.TipoPersona;
import org.jetbrains.annotations.NotNull;

public class AutorizacionMiddleware implements Handler {
    @Getter @Setter private Boolean debeSerAdmin = false;
    @Getter @Setter private Boolean debeEstarLogueado = false;
    @Getter @Setter private Boolean debeSerPH = false;
    @Getter @Setter private Boolean debeSerPJ = false;

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Roles userRole = context.sessionAttribute("rolUsuario");
        Boolean estaLogueado = context.sessionAttribute("logueado");
        TipoPersona tipoPersona = context.sessionAttribute("tipoPersona");

        if( estaLogueado == null ){ estaLogueado = false; }

        if(debeEstarLogueado && !estaLogueado ){
            context.redirect("/login");
        }

        if (userRole != null && estaLogueado) {
            if (debeSerAdmin && userRole.equals(Roles.USUARIO)) {
                context.redirect("/404");
            }
            if (tipoPersona != null && debeSerPJ && tipoPersona.equals(TipoPersona.PH)) {
                context.redirect("/404");
            }
            if (tipoPersona != null && debeSerPH && tipoPersona.equals(TipoPersona.PJ)) {
                context.redirect("/404");
            }
        }
    }
    public AutorizacionMiddleware setDebeSerAdmin() {
        this.debeSerAdmin = true;
        return this;
    }

    public AutorizacionMiddleware setDebeSerLogueado() {
        this.debeEstarLogueado = true;
        return this;
    }

    public AutorizacionMiddleware setDebeSerPF() {
        this.debeSerPH = true;
        return this;
    }

    public AutorizacionMiddleware setDebeSerPJ() {
        this.debeSerPJ = true;
        return this;
    }


}