package presentacion;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class DonarEntregarTarjetasController implements Handler {

    public DonarEntregarTarjetasController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = new HashMap<>();
        context.render("templates/.mustache", model); //TODO: COMPLETAR MUSTACHE

    }
}
