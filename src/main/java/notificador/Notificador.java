package notificador;

import notificador.MAIL.ApacheCommonsEmail;
import notificador.MAIL.StrategyMAIL;
import personas.TipoMedioDeContacto;
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
        estrategias.put(TipoMedioDeContacto.MAIL, mail);
    }

    public static void agregarEstrategia(TipoMedioDeContacto medio, StrategyNotificacion estrategia){
        estrategias.put(medio, estrategia);
    }

    //TODO

    /*public void notificar(String mensaje, Colaborador persona) {

        TipoMedioContacto tipo = persona;
        String email = persona.getEmail();

        if (estrategias.containsKey(contacto)) {
            estrategias.get(contacto).enviarNotificacion(mensaje, persona);
        } else {
            throw new NoExisteMetodoExcepcion();
        }
    }*/

    /*
        TipoMedioContacto contacto = persona.getContacto().get(0);
        if(estrategias.containsKey(contacto)){
            estrategias.get(contacto).enviarNotificacion(mensaje, persona);
        }else{
            throw new NoExisteMetodoExcepcion();
        }

        // de aca se llama al StrategyNotificacion correspondiente
    }
    */

}