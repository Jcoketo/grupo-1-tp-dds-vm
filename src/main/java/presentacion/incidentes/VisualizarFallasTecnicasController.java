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
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VisualizarFallasTecnicasController implements Handler {

    RepositorioIncidentes repoIncidentes = RepositorioIncidentes.getInstancia();

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        String idHel = context.queryParam("heladeraId");
        if (idHel == null || idHel.isEmpty()) {
            context.redirect("/mapaHeladeras");
            return;
        }
        int idHeladera = 0;
        try {
            idHeladera = Integer.parseInt(idHel);
        } catch (NumberFormatException | NullPointerException e) {
            context.redirect("/mapaHeladeras");
            return;
        }

        NotificacionAlerta notificacionAlerta = context.sessionAttribute("notificacionAlerta");
        if (notificacionAlerta != null) {
            model.put("notificacionTarjeta", notificacionAlerta);
        }
        context.consumeSessionAttribute("notificacionAlerta");

        Map<Boolean, List<FallasHeladera>> fallasHeladeraMap = repoIncidentes.obtenerIncidentes(idHeladera).stream().map(incidente -> {
            FallasHeladera falla = new FallasHeladera();
            falla.setId(incidente.getId());
            falla.setDescripcion(incidente.getDescripcion());
            falla.setEstaSolucionado(incidente.getEstaSolucionado());
            LocalDateTime fechaHoraIncidente = incidente.getFechaHoraIncidente();
            falla.setFechaHoraIncidente(falla.setFormato(fechaHoraIncidente));
            return falla;
        }).collect(Collectors.partitioningBy(FallasHeladera::getEstaSolucionado));

        List<FallasHeladera> noSolucionadas = fallasHeladeraMap.get(false).stream()
                .sorted(Comparator.comparing(FallasHeladera::getFechaHoraIncidente).reversed())
                .collect(Collectors.toList());

        List<FallasHeladera> solucionadas = fallasHeladeraMap.get(true).stream()
                .sorted(Comparator.comparing(FallasHeladera::getFechaHoraIncidente).reversed())
                .toList();

        noSolucionadas.addAll(solucionadas);

        model.put("nombreUsuario", context.sessionAttribute("nombreUsuario"));
        model.put("detalleFallas", noSolucionadas);
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

    public String setFormato(LocalDateTime fechaHoraIncidente) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm");
        return fechaHoraIncidente.format(formatter);
    }
}

