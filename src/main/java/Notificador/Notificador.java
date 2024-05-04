package Notificador;

import Notificador.MAIL.ApacheCommonsEmail;
import Notificador.MAIL.StrategyMAIL;
import lombok.Getter;
import personas.Colaborador;

import java.util.HashMap;
import java.util.Map;


public class Notificador {
    @Getter
    private static Map<String, StrategyNotificacion> estrategias;

    static {
        estrategias = new HashMap<String, StrategyNotificacion>();
        StrategyWPP wpp = new StrategyWPP();
        wpp.setAdapter(new WhatsappTwilio());
        estrategias.put("WPP", wpp);

        StrategyMAIL mail = new StrategyMAIL();
        mail.setAdapter(new ApacheCommonsEmail());
        estrategias.put("MAIL", mail);
    }
    hogar.

    public static void agregarEstrategia(String nombre, StrategyNotificacion estrategia){
        estrategias.put(nombre, estrategia);
    }

    public static void notificar(Notificable notificable, Colaborador persona) {
        notificar(notificable.getInfo(), persona);
    }

    public static void notificar(String mensaje, Colaborador persona) {
        String metodo = persona.getMetodoNotificacion();
        if(estrategias.containsKey(metodo)){
            estrategias.get(metodo).enviarNotificacion(mensaje, persona);
        }else{
            throw new NoExisteMetodoExcepcion();
        }
    }
}