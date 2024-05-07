package Notificador;

import Notificador.MAIL.ApacheCommonsEmail;
import Notificador.MAIL.StrategyMAIL;
import enums.MedioContacto;
import lombok.Getter;
import personas.Colaborador;

import java.util.HashMap;
import java.util.Map;


public class Notificador {
    @Getter
    private static Map<MedioContacto, StrategyNotificacion> estrategias;

    static {
        estrategias = new HashMap<MedioContacto, StrategyNotificacion>();

        StrategyMAIL mail = new StrategyMAIL();
        mail.setAdapter(new ApacheCommonsEmail());
        estrategias.put(MedioContacto.MAIL, mail);
    }

    public static void agregarEstrategia(MedioContacto medio, StrategyNotificacion estrategia){
        estrategias.put(medio, estrategia);
    }

    public void notificar(String mensaje, Colaborador persona) {
        if(persona.getContacto().isEmpty()){
            throw new NoTieneMetodoExcepcion();
        }
        MedioContacto contacto = persona.getContacto().get(0);
        if(estrategias.containsKey(contacto)){
            estrategias.get(contacto).enviarNotificacion(mensaje, persona);
        }else{
            throw new NoExisteMetodoExcepcion();
        }

        // de aca se llama al StrategyNotificacion correspondiente
    }
}