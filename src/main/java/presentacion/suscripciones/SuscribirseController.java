package presentacion.suscripciones;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.authService.AuthServiceSuscripcion;
import modelo.colaboracion.FrecuenciaDonacion;
import modelo.excepciones.ExcepcionValidacion;
import modelo.personas.MedioDeContacto;
import modelo.personas.TipoMedioDeContacto;
import modelo.suscripcion.TipoSuscripcion;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class SuscribirseController implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = context.sessionAttribute("model");
        if (model == null) {
            model = new HashMap<>();
            context.sessionAttribute("model", model);
        }

        Integer idPersona = context.sessionAttribute("idPersona");
        Integer idHeladera = context.sessionAttribute("idHeladera");
        String tipoSuscripcion = context.sessionAttribute("tipoSuscripcion");
        String limiteMinimo = context.sessionAttribute("limiteMinimo"); // CASO 1
        String limiteMaximo = context.sessionAttribute("limiteMaximo"); // CASO 2

        String medioDeContacto = context.formParam("medioDeContacto"); //enum

        TipoSuscripcion tipo;
        switch (tipoSuscripcion) {
            case "01" -> tipo = TipoSuscripcion.QUEDAN_POCAS;
            case "02" -> tipo = TipoSuscripcion.POCO_ESPACIO;
            case "03" -> tipo = TipoSuscripcion.DESPERFECTO;
            default -> tipo = null;
        }

        TipoMedioDeContacto medio;
        switch (medioDeContacto) {
            case "01" -> medio = TipoMedioDeContacto.MAIL;
            case "02" -> medio = TipoMedioDeContacto.WHATSAPP;
            case "03" -> medio = TipoMedioDeContacto.TELEGRAM;
            default -> medio = null;
        }

        try {
            AuthServiceSuscripcion.generarSuscripcion(idHeladera, idPersona, tipo, Integer.parseInt(limiteMinimo), Integer.parseInt(limiteMaximo), medio);
        }
        catch (ExcepcionValidacion e)
        {
            e.printStackTrace();
        }



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
