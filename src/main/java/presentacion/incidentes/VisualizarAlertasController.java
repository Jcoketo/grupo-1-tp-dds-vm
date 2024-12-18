package presentacion.incidentes;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.Getter;
import lombok.Setter;
import modelo.elementos.Alerta;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioHeladeras;
import persistencia.RepositorioIncidentes;
import utils.GeneradorModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisualizarAlertasController implements Handler {

    private static RepositorioIncidentes repoIncidentes = RepositorioIncidentes.getInstancia();

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        String idHeladera = context.queryParam("heladeraId");
        if (idHeladera == null || idHeladera.isEmpty()) {
            context.redirect("/mapaHeladeras");
            return;
        }

        List<AlertasXHeladera> alertitas;

        List<Alerta> alertas = repoIncidentes.traerAlertasPorHeladera(Integer.parseInt(idHeladera));
        if ( alertas == null)
            alertitas = List.of();
        else {
            alertitas = alertas.stream().map(alerta -> {
                AlertasXHeladera alertaAux = new AlertasXHeladera();
                alertaAux.setTipo(alerta.getTipoAlerta().toString().replace("_", " "));
                alertaAux.setFecha(alerta.getFechaHoraIncidente().format(DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm")));
                alertaAux.setEstado(alerta.getEstaSolucionado());
                return alertaAux;
            }).toList();
        }

        model.put("alertas", alertitas);
        context.render("templates/visualizarAlertas.mustache", model);
    }
}
@Getter
@Setter
class AlertasXHeladera {
    private String tipo;
    private String fecha;
    private Boolean estado;
}


