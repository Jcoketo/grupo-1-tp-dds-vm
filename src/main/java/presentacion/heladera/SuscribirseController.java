package presentacion.heladera;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.Getter;
import lombok.Setter;
import modelo.authService.AuthServiceSuscripcion;
import modelo.excepciones.ExcepcionValidacion;
import modelo.personas.TipoMedioDeContacto;
import modelo.suscripcion.TipoSuscripcion;
import org.jetbrains.annotations.NotNull;
import utils.GeneradorModel;
import java.util.Map;

public class SuscribirseController implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        Integer idPersona = context.sessionAttribute("idPersona");
        String idHel = context.formParam("heladeraId");
        String tipoSuscripcion = context.formParam("tipoSuscripcion");
        String lim = context.formParam("cantidad"); // CASO 1
        String medioDeContacto = context.formParam("medioDeContacto"); //enum

        // ------------- DATOS DE LA HELADERA ACTUAL ---------------- //
        String id = context.formParam("heladeraId");
        String nombre = context.formParam("nombre");
        String direccion = context.formParam("direccion");
        String lat = context.formParam("latitud");
        String longi = context.formParam("longitud");
        String fecha = context.formParam("fecha");
        String estado = context.formParam("estado");
        String disp = context.formParam("disponibilidad");

        Double latitud = 0.0;
        Double longitud = 0.0;
        Integer disponibilidad = 0;

        try {
            if (lat == null || longi == null) {
                throw new NullPointerException("Latitude or Longitude is null");
            }
            latitud = Double.parseDouble(lat);
            longitud = Double.parseDouble(longi);
        } catch (NumberFormatException | NullPointerException e) {
            latitud = 0.0;
            longitud = 0.0;
        }
        if (disp != null) { disponibilidad = Integer.parseInt(disp); }

        if (idHel == null || idHel.equals("")) {
            context.redirect("/visualizarHeladeras");
            return;
        }
        Integer idHeladera = Integer.parseInt(idHel);

        if (lim == null || lim.equals("")) {
            lim = "0";
        }
        Integer limite = Integer.parseInt(lim);

        NotificacionSuscripcion notificacionSuscripcion = new NotificacionSuscripcion();

        TipoSuscripcion tipo;
        switch (tipoSuscripcion) {
            case "1" -> tipo = TipoSuscripcion.QUEDAN_POCAS;
            case "2" -> tipo = TipoSuscripcion.POCO_ESPACIO;
            case "3" -> tipo = TipoSuscripcion.DESPERFECTO;
            default -> tipo = null;
        }

        TipoMedioDeContacto medio;
        switch (medioDeContacto) {
            case "MAIL" -> medio = TipoMedioDeContacto.MAIL;
            case "TELEFONO" -> medio = TipoMedioDeContacto.TELEFONO;
            case "TELEGRAM" -> medio = TipoMedioDeContacto.TELEGRAM;
            default -> medio = null;
        }

        try {
            AuthServiceSuscripcion.generarSuscripcion(idHeladera, idPersona, tipo, limite, medio);
        }
        catch (ExcepcionValidacion e)
        {
            notificacionSuscripcion.error(e.getMessage());
            context.sessionAttribute("notificacionSuscripcion", notificacionSuscripcion);

            String redirectUrl = String.format("/visualizarDetalleHeladera?heladeraId=%s&nombre=%s&direccion=%s&lat=%s&long=%s&fecha=%s&estado=%s&disponibilidad=%s",
                    id, nombre, direccion, latitud, longitud, fecha, estado, disponibilidad);
            context.redirect(redirectUrl);

            return;
        }

        notificacionSuscripcion.aprobada("Suscripción realizada con éxito!");
        context.sessionAttribute("notificacionSuscripcion", notificacionSuscripcion);
        String redirectUrl = String.format("/visualizarDetalleHeladera?heladeraId=%s&nombre=%s&direccion=%s&lat=%s&long=%s&fecha=%s&estado=%s&disponibilidad=%s",
                id, nombre, direccion, latitud, longitud, fecha, estado, disponibilidad);
        context.redirect(redirectUrl);

    }
}
@Getter
@Setter
class NotificacionSuscripcion{
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

/*
* Suscripciones
Los colaboradores pueden optar por suscribirse a las heladeras que se encuentren en zonas donde
frecuentan y ser notificados en los siguientes casos (el colaborador puede decidir en qué caso/s quiere ser
notificado):
1. Quedan únicamente n viandas disponibles en la heladera, siendo n un número que el colaborador
puede setear. Un colaborador distribuidor puede llevar N viandas a la heladera para que esté más
llena.
2. Faltan n viandas para que la heladera esté llena y no se puedan ingresar más viandas. Un colaborador
distribuidor puede llevar N viandas a otra heladera que está menos llena.
3. La heladera sufrió un desperfecto y las viandas deben ser llevadas a otras heladeras a la brevedad
para que las mismas no se echen a perder. El sistema debe sugerirle al colaborador a que heladera/s
puede llevar las viandas, luego el colaborador debe aceptar o rechazar esta sugerencia. Vale aclarar
que la sugerencia puede estar compuesta por más de una heladera, ya que en la mayoría de los casos
la heladera más cercana no tiene la capacidad que se necesita para registrar las nuevas viandas
En caso de acudir a realizar algunas de estas tareas, el colaborador deberá registrar una distribución de
viandas como una forma de contribución. Vamos a asumir que los distribuidores son responsables para
llevar a cabo dicha tarea con criterio. También, una vez registrada esta colaboración se debe actualizar el
stock de cada heladera.
Se deberá realizar las integraciones correspondientes para que en los casos descritos los usuarios sean
notificados por el medio de comunicación deseado (Mail, WhatsApp o Telegram). Deberá quedar registro
del mensaje enviado a cada usuario.

* */
