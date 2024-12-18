package presentacion.incidentes;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.Getter;
import lombok.Setter;
import modelo.elementos.FallaTecnica;
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
        } catch (NumberFormatException e) {
            context.redirect("/mapaHeladeras");
            return;
        }

        NotificacionAlerta notificacionAlerta = context.sessionAttribute("notificacionAlerta");
        if (notificacionAlerta != null) {
            model.put("notificacionAlerta", notificacionAlerta);
            context.consumeSessionAttribute("notificacionAlerta");
        }

        List<FallaTecnica> fallasHeladera = repoIncidentes.obtenerFallasXHeladera(idHeladera);
        if (fallasHeladera == null )
            fallasHeladera = List.of();


        List<FallasHeladera> fallitas = fallasHeladera.stream()
                .map(fallaTecnica -> {
                    FallasHeladera fallo = new FallasHeladera();
                    fallo.setId(fallaTecnica.getId());
                    fallo.setDescripcion(fallaTecnica.getDescripcion());
                    fallo.setFechaHoraIncidente(fallaTecnica.getFechaHoraIncidente().format(DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm")));
                    fallo.setEstaSolucionado(fallaTecnica.getEstaSolucionado());
                    return fallo;
                }).toList();

        List<FallasHeladera> noSolucionadas = fallitas.stream()
                .filter(falla -> !falla.getEstaSolucionado())
                .sorted(Comparator.comparing(FallasHeladera::getFechaHoraIncidente).reversed())
                .collect(Collectors.toList());

        List<FallasHeladera> solucionadas = fallitas.stream()
                .filter(FallasHeladera::getEstaSolucionado)
                .sorted(Comparator.comparing(FallasHeladera::getFechaHoraIncidente).reversed())
                .collect(Collectors.toList());

        noSolucionadas.addAll(solucionadas);

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

