package modelo.notificador;

import modelo.notificador.mail.ApacheCommonsEmail;
import modelo.notificador.mail.StrategyMAIL;
import modelo.personas.Colaborador;
import modelo.notificador.telegram.StrategyTelegram;
import modelo.notificador.whatsApp.NotificadorWhatsApp;
import modelo.notificador.whatsApp.StrategyWhatsApp;
import modelo.personas.TipoMedioDeContacto;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;


public class Notificador {
    @Getter
    private static Map<TipoMedioDeContacto, StrategyNotificacion> estrategias;

    static {
        estrategias = new HashMap<TipoMedioDeContacto, StrategyNotificacion>();

        StrategyMAIL mail = new StrategyMAIL();
        mail.setAdapter(new ApacheCommonsEmail());

        StrategyWhatsApp whatsApp = new StrategyWhatsApp();
        whatsApp.setAdapter(new NotificadorWhatsApp());

        StrategyTelegram telegram = new StrategyTelegram();

        agregarEstrategia(TipoMedioDeContacto.TELEGRAM, telegram);
        agregarEstrategia(TipoMedioDeContacto.WHATSAPP, whatsApp);
        agregarEstrategia(TipoMedioDeContacto.MAIL, mail);
    }

    public static void agregarEstrategia(TipoMedioDeContacto medio, StrategyNotificacion estrategia){
        estrategias.put(medio, estrategia);
    }

    public static void notificarXNuevoUsuario(String mensaje, Colaborador persona) {
        if (persona.getEmail() == null) {
            throw new IllegalArgumentException("El colaborador no tiene un email asignado.");
        }

        if (estrategias.containsKey(TipoMedioDeContacto.MAIL)) {
            estrategias.get(TipoMedioDeContacto.MAIL).enviarNotificacion(mensaje, persona, "Bienvenido");
        } else {
            throw new NoExisteMetodoExcepcion();
        }
    }

    public static void notificar(String mensaje, String asunto, Colaborador persona, TipoMedioDeContacto contacto) {
        if(estrategias.containsKey(contacto)){
            estrategias.get(contacto).enviarNotificacion(mensaje, persona, asunto);
        }else{
            throw new NoExisteMetodoExcepcion();
        }
    }
}