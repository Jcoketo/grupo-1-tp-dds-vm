package presentacion;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DonarViandaController implements Handler {

    @Override
    public void handle(@NotNull Context context) throws Exception {
        String name = context.queryParam("name");
        String address = context.queryParam("address");
        String status = context.queryParam("status");
        String date = context.queryParam("date");

        Map<String, Object> model = new HashMap<>();
        model.put("name", name);
        model.put("address", address);
        model.put("status", status);
        model.put("date", date);

        context.render("templates/donarVianda.mustache", model);
    }
}
