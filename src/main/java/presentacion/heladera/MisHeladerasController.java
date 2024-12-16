package presentacion.heladera;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import utils.GeneradorModel;

import java.util.Map;

public class MisHeladerasController implements Handler {

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        String mensajeBaja = context.sessionAttribute("mensajeBaja");
        if (mensajeBaja != null) {
            model.put("mensajeBaja", mensajeBaja);
            context.consumeSessionAttribute("mensajeBaja");
        }


        context.render("templates/misHeladeras.mustache",model);

    }
}


