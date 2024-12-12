package presentacion.colaboraciones;

import lombok.Getter;
import lombok.Setter;
import modelo.colaboracion.Colaboracion;
import modelo.colaboracion.DonarDinero;
import org.jetbrains.annotations.NotNull;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import persistencia.RepositorioColaboradores;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MisColaboracionesController implements Handler{

    private RepositorioColaboradores repoColaboradores = RepositorioColaboradores.getInstancia();
    

public MisColaboracionesController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = context.sessionAttribute("model");
        if (model == null) {
            model = new HashMap<>();
            context.sessionAttribute("model", model);
        }

        Integer idPersona = context.sessionAttribute("idPersona");

        List<DatosColaboracion> datosColaboraciones = getDatosColaboraciones(repoColaboradores.getColaboraciones(idPersona));

        model.put("colabs", datosColaboraciones);

        model.put("nombreUsuario", context.sessionAttribute("nombreUsuario"));
        context.render("templates/misColaboraciones.mustache", model);
    }

    private List<DatosColaboracion> getDatosColaboraciones(List<Colaboracion> colaboraciones){
        return colaboraciones.stream().map(colab -> new DatosColaboracion(colab.getClassName(), colab.getFechaColaboracion(), colab.conocerPuntaje(), colab.getPorCSV())).toList();
    }


}
@Getter
@Setter
class DatosColaboracion {
    private String tipo;
    private LocalDate fechaDeColaboracion;
    private Double puntosSumados;
    private Boolean xCSV;

    public DatosColaboracion(String tipo, LocalDate fechaDeColaboracion, Double puntosSumados, Boolean xCSV ) {
        this.tipo = tipo;
        this.fechaDeColaboracion = fechaDeColaboracion;
        this.puntosSumados = puntosSumados;
        this.xCSV = xCSV;

    }
}
