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

        Integer idPersona = context.sessionAttribute("idPersona");
        Colaborador colaborador = repositorioColaboradores.buscarColaboradorXIdPersona(idPersona);

        List<Suscripcion> suscripciones = repositorioSuscripciones.obtenerSuscripciones(colaborador);
        if(suscripciones == null){
            suscripciones = List.of();
        }

        List<DatosSuscripcion> datosSuscripciones = this.getDatosSuscripciones(suscripciones);

        model.put("suscripciones", datosSuscripciones);

        context.render("templates/misSuscripciones.mustache", model);
    }

    public List<DatosSuscripcion> getDatosSuscripciones(List<Suscripcion> suscripciones) {
        return suscripciones.stream().map(suscripcion ->
            new DatosSuscripcion(suscripcion.getHeladera().getNombre(),
                    suscripcion.getTipoSuscripcion().toString(),
                    suscripcion.getMedioDeContacto().getTipo().toString(),
                    suscripcion.getBajaLogica())
        ).toList();
    }
}

@Getter
@Setter
class DatosSuscripcion {
    private String nombreHeladera;
    private String tipoSuscripcion;
    private String medioDeContacto;
    private Boolean bajaLogica;

    public DatosSuscripcion(String nombreHeladera, String tipoSuscripcion, String medioDeContacto, Boolean bajaLogica) {
        this.nombreHeladera = nombreHeladera;
        this.tipoSuscripcion = tipoSuscripcion.replace("_", " ");
        this.medioDeContacto = medioDeContacto;
        this.bajaLogica = bajaLogica;
    }
}
