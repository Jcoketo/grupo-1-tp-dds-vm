package presentacion.colaboraciones;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.Getter;
import lombok.Setter;
import modelo.authService.AuthServiceColaboracion;
import modelo.excepciones.ExcepcionValidacion;
import modelo.personas.PersonaHumana;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioColaboradores;
import utils.GeneradorModel;

import java.util.Map;

public class SolicitudDeTarjetasController implements Handler {

    private RepositorioColaboradores repositorioColaboradores = RepositorioColaboradores.getInstancia();

    public SolicitudDeTarjetasController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        Integer IdPersona = context.sessionAttribute("idPersona");
        NotificacionTarjeta notificacionTarjeta = new NotificacionTarjeta();

        PersonaHumana persona = repositorioColaboradores.buscarPersonaPorId(IdPersona);

        if ( persona == null) {
            notificacionTarjeta.error("No se encontro la persona");
            context.sessionAttribute("notificacionTarjeta", notificacionTarjeta);
            context.redirect("/elegirDonacion");
            return;
        }

        if ( persona.getDireccion() == null || persona.getDireccion().equals("")) {
            notificacionTarjeta.error("Debes tener una direccion cargada para solicitar tarjetas");
            context.sessionAttribute("notificacionTarjeta", notificacionTarjeta);
            context.redirect("/elegirDonacion");
            return;
        }

        try {
            AuthServiceColaboracion.registrarPersonasVulnerables(IdPersona);
            notificacionTarjeta.aprobada("Tu pedido de tarjetas esta en camino!");
        } catch (ExcepcionValidacion e) {
            notificacionTarjeta.error(e.getMessage());
        }

        context.sessionAttribute("notificacionTarjeta", notificacionTarjeta);
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