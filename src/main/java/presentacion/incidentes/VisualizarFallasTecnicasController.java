package presentacion.incidentes;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioIncidentes;
import utils.GeneradorModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class VisualizarFallasTecnicasController implements Handler{

    RepositorioIncidentes repoIncidentes = RepositorioIncidentes.getInstancia();


    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        String idHel = context.queryParam("heladeraId");
        if (idHel == null || idHel.isEmpty()) {
            context.redirect("/visualizarFallasTecnicas?heladeraId=" + idHel);
            return;
        }
        int idHeladera = Integer.parseInt(idHel);

        NotificacionAlerta notificacionAlerta = context.sessionAttribute("notificacionAlerta");
        if(notificacionAlerta != null){
            model.put("notificacionTarjeta", notificacionAlerta);
        }
        context.consumeSessionAttribute("notificacionAlerta");

        List<FallasHeladera> fallasHeladera = repoIncidentes.obtenerIncidentes(idHeladera).stream().map(incidente -> {
            FallasHeladera falla = new FallasHeladera();
            falla.setId(incidente.getId());
            falla.setDescripcion(incidente.getDescripcion());
            falla.setEstaSolucionado(incidente.getEstaSolucionado());
            LocalDateTime fechaHoraIncidente = incidente.getFechaHoraIncidente();
            falla.setFechaHoraIncidente(falla.setFormato(fechaHoraIncidente));
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
    private String fechaHoraIncidente;
    private Boolean estaSolucionado;

    public String setFormato(LocalDateTime fechaHoraIncidente){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm");
        return fechaHoraIncidente.format(formatter);
    }
}

