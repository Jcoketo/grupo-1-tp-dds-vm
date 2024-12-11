package presentacion;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class LogoutController implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {
        context.consumeSessionAttribute("idPersona");
        context.consumeSessionAttribute("nombreUsuario");
        context.consumeSessionAttribute("logueado");
        context.consumeSessionAttribute("rolUsuario");
        context.consumeSessionAttribute("tipoPersona");
        context.redirect("/inicio");



    }
}
