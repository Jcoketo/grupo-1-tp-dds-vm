package presentacion.colaboraciones;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.Getter;
import lombok.Setter;
import modelo.personas.Colaborador;
import modelo.suscripcion.Suscripcion;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioColaboradores;
import persistencia.RepositorioSuscripciones;
import utils.GeneradorModel;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class MisSuscripcionesController implements Handler {
    private RepositorioSuscripciones repositorioSuscripciones = RepositorioSuscripciones.getInstancia();
    private RepositorioColaboradores repositorioColaboradores = RepositorioColaboradores.getInstancia();

    public MisSuscripcionesController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        if ( context.sessionAttribute("notificacionBajaSuscripcion") != null ) {
            model.put("notificacionBajaSuscripcion", context.sessionAttribute("notificacionBajaSuscripcion"));
            context.consumeSessionAttribute("notificacionBajaSuscripcion");
        }

        Integer idPersona = context.sessionAttribute("idPersona");
        Colaborador colaborador = repositorioColaboradores.buscarColaboradorXIdPersona(idPersona);

        List<Suscripcion> suscripciones = repositorioSuscripciones.obtenerSuscripciones(colaborador.getId());
        if(suscripciones == null){
            suscripciones = List.of();
        }

        List<DatosSuscripcion> datosSuscripciones = this.getDatosSuscripciones(suscripciones);

        model.put("suscripciones", datosSuscripciones);

        context.render("templates/misSuscripciones.mustache", model);
    }

    public List<DatosSuscripcion> getDatosSuscripciones(List<Suscripcion> suscripciones) {
        return suscripciones.stream()
                .map(suscripcion -> {
                    String nombreHeladera = suscripcion.getHeladera() != null ? suscripcion.getHeladera().getNombre() : "";
                    return new DatosSuscripcion(suscripcion.getId(),
                            nombreHeladera,
                            suscripcion.getTipoSuscripcion().toString(),
                            suscripcion.getMedioDeContacto().getTipo().toString(),
                            suscripcion.getBajaLogica());
                })
                .sorted(Comparator.comparing(DatosSuscripcion::getBajaLogica))
                .toList();
    }
}

@Getter
@Setter
class DatosSuscripcion {
    private int idSuscripcion;
    private String nombreHeladera;
    private String tipoSuscripcion;
    private String medioDeContacto;
    private Boolean bajaLogica;

    public DatosSuscripcion(int idSuscripcion, String nombreHeladera, String tipoSuscripcion, String medioDeContacto, Boolean bajaLogica) {
        this.nombreHeladera = nombreHeladera;
        this.tipoSuscripcion = tipoSuscripcion.replace("_", " ");
        this.medioDeContacto = medioDeContacto;
        this.bajaLogica = bajaLogica;
        this.idSuscripcion = idSuscripcion;
    }
}
