package presentacion.heladera;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.Getter;
import lombok.Setter;
import modelo.personas.MedioDeContacto;
import modelo.personas.Persona;
import modelo.personas.TipoMedioDeContacto;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioColaboradores;
import utils.GeneradorModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VisualizarDetalleHeladeraController implements Handler{

    RepositorioColaboradores repoColaboradores = RepositorioColaboradores.getInstancia();

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);
        Boolean estaLogueado = context.sessionAttribute("logueado");
        Integer idPersona = context.sessionAttribute("idPersona");
        model.put("logueado", estaLogueado != null && estaLogueado);
        model.put("nombreUsuario", context.sessionAttribute("nombreUsuario"));

        NotificacionSuscripcion notificacionSuscripcion = context.sessionAttribute("notificacionSuscripcion");
        if(notificacionSuscripcion != null){
            model.put("notificacionSuscripcion", notificacionSuscripcion);
        }
        context.consumeSessionAttribute("notificacionTarjeta");

        Persona persona = repoColaboradores.obtenerPersona(idPersona);

        List<TipoMedioDeContacto> mediosDeContacto = persona.getMediosDeContacto().stream().map(MedioDeContacto::getTipo).toList();
        if (mediosDeContacto == null) {
            mediosDeContacto = new ArrayList<>();
        }

        List<Medio> medios = new ArrayList<>();
        for (TipoMedioDeContacto medio : mediosDeContacto) {
                Medio m = new Medio();
                m.setTipo(medio);
                medios.add(m);
        }
        model.put("medios", medios);

        String id = context.queryParam("heladeraId");
        String nombre = context.queryParam("nombre");
        String direccion = context.queryParam("direccion");
        Double latitud = Double.parseDouble(context.queryParam("lat"));
        Double longitud = Double.parseDouble(context.queryParam("long"));
        String fecha = context.queryParam("fecha");

        String estado = context.queryParam("estado");
        Integer disponibilidad = Integer.parseInt(context.queryParam("disponibilidad"));

        model.put("heladeraId",id);
        model.put("nombre",nombre);
        model.put("direccion",direccion);
        model.put("latitud",latitud);
        model.put("longitud",longitud);

        model.put("fecha",fecha);
        model.put("estado",estado);
        model.put("disponibilidad",disponibilidad);

        context.render("templates/visualizarDetalleHeladera.mustache", model);

    }
}

@Getter
@Setter
class Medio{
    private TipoMedioDeContacto tipo;
}