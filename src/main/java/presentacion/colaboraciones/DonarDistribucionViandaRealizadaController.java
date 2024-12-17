package presentacion.colaboraciones;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.authService.AuthServiceColaboracion;
import modelo.colaboracion.MotivoDistribucion;
import modelo.excepciones.ExcepcionValidacion;
import org.jetbrains.annotations.NotNull;
import utils.GeneradorModel;

import java.util.Map;

public class DonarDistribucionViandaRealizadaController implements Handler {

    public DonarDistribucionViandaRealizadaController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        Integer idHeladeraOrigen = null;
        Integer idHeladeraDestino = null;

        try {
            idHeladeraOrigen = Integer.parseInt(context.sessionAttribute("id_heladera_origen"));
            idHeladeraDestino = Integer.parseInt(context.sessionAttribute("id_heladera_destino"));
        }catch (ExcepcionValidacion e){
            model.put("errorDistribuir", e.getMessage());
            //context.status(400);
            context.redirect("/donarDistribuirViandas");
            return;
        }


        Integer motivoDistribucion = Integer.parseInt(context.formParam("motivoDistribucion"));
        Integer cantidadViandas = Integer.parseInt(context.formParam("cantidadViandas")); // --> por defecto es 1

        if (idHeladeraOrigen.equals(idHeladeraDestino)) {
            model.put("errorDistribuir", "La Heladera de origen no puede ser la misma que la de Destino!");
            context.redirect("/donarDistribuirViandas");
            return;
        }

        MotivoDistribucion motivo;
        if (motivoDistribucion == 1) {
            motivo = MotivoDistribucion.DESPERFECTO;
        }else  {
            motivo = MotivoDistribucion.FALTA_VIANDAS;
        }

        try{
            Integer idPersona = context.sessionAttribute("idPersona");
            AuthServiceColaboracion.registrarColaboracionDistribuirViandas(idPersona ,idHeladeraOrigen, idHeladeraDestino, motivo, cantidadViandas);
        }
        catch (ExcepcionValidacion e){
            model.put("errorDistribuir", e.getMessage());
            //context.status(400);
            context.redirect("/donarDistribuirViandas");
            return;
        }

        context.redirect("/graciasPorDonar");
    }


}

