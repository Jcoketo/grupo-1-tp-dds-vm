package modelo.notificador.telegram;

import modelo.notificador.StrategyNotificacion;
import modelo.personas.MedioDeContacto;

public class StrategyTelegram implements StrategyNotificacion {

    @Override
    public void enviarNotificacion(String mensaje, MedioDeContacto medioDeContacto, String asunto) {

        TelegramBot bot = new TelegramBot();
        bot.enviarMensaje(mensaje, medioDeContacto.getContacto()); //Aca deberia ir el numero de chat de la persona

    }

}
