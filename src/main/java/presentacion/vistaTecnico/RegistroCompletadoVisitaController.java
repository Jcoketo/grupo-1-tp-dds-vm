package presentacion.vistaTecnico;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.authService.AuthServiceTecnico;
import org.jetbrains.annotations.NotNull;
import utils.GeneradorModel;

import java.util.Map;

public class RegistroCompletadoVisitaController implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        Integer idTecnico = context.sessionAttribute("idTecnico");
        String idHel = context.queryParam("idHeladera");
        String idInc = context.queryParam("idIncidente");
        String checkBoxResolvioProblema = context.queryParam("checkBoxResolvioProblema");
        String descripcion = context.queryParam("descripcion");
        String URLfoto = context.queryParam("URLfoto"); // La foto es opcional.

        Integer idHeladera = Integer.parseInt(idHel);
        Integer idIncidente = Integer.parseInt(idInc);

        Boolean problemaResuelto = Boolean.FALSE;
        if (checkBoxResolvioProblema.equals("on")) {
            problemaResuelto = Boolean.TRUE;;
        }

        try {
            AuthServiceTecnico.registrarVisita(idTecnico, idHeladera, idIncidente, descripcion, URLfoto, problemaResuelto);

        } catch (Exception e) {
            model.put("error", e.getMessage());
            context.render("templates/error.mustache", model);
        }

    }
}
