package presentacion.incidentes;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.Getter;
import lombok.Setter;
import modelo.elementos.PuntoEstrategico;
import modelo.excepciones.ExcepcionValidacion;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioIncidentes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisualizarFallasTecnicasController implements Handler{

    RepositorioIncidentes repoIncidentes = RepositorioIncidentes.getInstancia();


    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = context.sessionAttribute("model");
        if (model == null) {
            model = new HashMap<>();
            context.sessionAttribute("model", model);
        }

        String idHel = context.queryParam("heladeraId");
        if (idHel == null || idHel.isEmpty()) {
            context.redirect("/visualizarFallasTecnicas?heladeraId=" + idHel);
            return;
        }
        int idHeladera = Integer.parseInt(idHel);

        List<FallasHeladera> fallasHeladera = repoIncidentes.obtenerIncidentes(idHeladera).stream().map(incidente -> {
            FallasHeladera falla = new FallasHeladera();
            falla.setId(incidente.getId());
            falla.setDescripcion(incidente.getDescripcion());
            falla.setEstaSolucionado(incidente.getEstaSolucionado());
            falla.setFechaHoraIncidente(incidente.getFechaHoraIncidente());
            return falla;
        }).toList();

        model.put("nombreUsuario", context.sessionAttribute("nombreUsuario"));
        model.put("detalleFallas", fallasHeladera);
        model.put("heladeraId", idHeladera);
        context.render("templates/visualizarFallasTecnicas.mustache", model);
    }
}

@Getter
@Setter
class FallasHeladera {
    private int id;
    private String descripcion;
    private LocalDateTime fechaHoraIncidente;
    private Boolean estaSolucionado;

}