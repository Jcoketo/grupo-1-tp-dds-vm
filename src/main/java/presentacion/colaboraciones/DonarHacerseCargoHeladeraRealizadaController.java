package presentacion.colaboraciones;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.authService.AuthServiceColaboracion;
import modelo.excepciones.ExcepcionValidacion;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class DonarHacerseCargoHeladeraRealizadaController implements Handler {

    public DonarHacerseCargoHeladeraRealizadaController() {
        super();
    }

    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = context.sessionAttribute("model");
        if (model == null) {
            model = new java.util.HashMap<>();
            context.sessionAttribute("model", model);
        }



        try{
            Integer idPersona = context.sessionAttribute("idPersona");
            //AuthServiceColaboracion.registrarColaboracionHeladera(idPersona);
        }
        catch (ExcepcionValidacion e){
            model.put("errorHeladera", e.getMessage());
            //context.status(400);
            context.redirect("/donarHacerseCargoHeladera");
            return;
        }


        context.redirect("/graciasPorDonar");
    }
}
