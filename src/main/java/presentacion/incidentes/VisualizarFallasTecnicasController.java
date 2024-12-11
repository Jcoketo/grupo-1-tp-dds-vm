package presentacion.incidentes;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.Getter;
import lombok.Setter;
import modelo.elementos.PuntoEstrategico;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class VisualizarFallasTecnicasController implements Handler{


    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = context.sessionAttribute("model");
        if (model == null) {
            model = new HashMap<>();
            context.sessionAttribute("model", model);
        }

        String idHel = context.queryParam("heladeraId");

        int IdHeladera = 0;
        if (idHel != null) {
            IdHeladera = Integer.parseInt(idHel);
        }

        model.put("nombreUsuario", context.sessionAttribute("nombreUsuario"));
        context.render("templates/visualizarFallasTecnicas.mustache", model);
    }

}

@Getter
@Setter
class FallasHeladera {
    private int id;
    private String nombre;
    private Integer disponibilidad;
    private Boolean activa;
    private Integer necesitaTrasladoDe;
    private PuntoEstrategico punto;
    private LocalDate fechaFuncionamiento;

}