package presentacion.colaboraciones;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.Getter;
import lombok.Setter;
import modelo.authService.AuthServiceColaboracion;
import modelo.excepciones.ExcepcionValidacion;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioColaboradores;
import utils.GeneradorModel;

import java.util.Map;

public class SolicitudDeTarjetasController implements Handler {

    private RepositorioColaboradores repoColab;

    public SolicitudDeTarjetasController(RepositorioColaboradores repo) {
        super();
        this.repoColab = repo;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        Integer IdPersona = context.sessionAttribute("idPersona");

        NotificacionTarjeta notificacionTarjeta = new NotificacionTarjeta();

        try {
            AuthServiceColaboracion.registrarPersonasVulnerables(IdPersona);
            notificacionTarjeta.aprobada("Tu pedido de tarjetas esta en camino!");
        } catch (ExcepcionValidacion e) {
            notificacionTarjeta.error(e.getMessage());
        }

        context.sessionAttribute("notificacionTarjeta", notificacionTarjeta);

        //context.render("templates/elegirDonacionFisica.mustache", model);
        context.redirect("/elegirDonacion");
    }
}

@Getter @Setter
class NotificacionTarjeta{
    private String tipo;
    private String mensaje;

    public void aprobada(String mensaje){
        this.mensaje = mensaje;
        this.tipo = "success";
    }
    public void error(String mensaje){
        this.mensaje = mensaje;
        this.tipo = "danger";
    }
}