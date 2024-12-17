package presentacion.vistaTecnico;

import accessManagment.Roles;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import utils.GeneradorModel;

import java.util.Map;

public class InicioTecnicoController implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        context.render("templates/inicioTecnico.mustache", model);
    }
}
