package notificaciones;

import modelo.notificador.telegram.TelegramBot;
import modelo.notificador.whatsApp.NotificadorWhatsApp;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;


public class notificadores {

    @Test
    @DisplayName("Prueba Notificacion por Telegram.")
    public void notificarPorTelegramTest() {
        TelegramBot telegramBot = new TelegramBot();
        telegramBot.enviarMensaje("Este es un mensaje de prueba", "6244017669");

        Assertions.assertTrue(true);

    }

    @Test
    @DisplayName("Prueba Notificacion por WhatsApp.")
    public void notificarPorWhatsAppTest() {
        NotificadorWhatsApp notificadorWhatsApp = new NotificadorWhatsApp();
        notificadorWhatsApp.enviarWhatsApp("Este es un mensaje de prueba", "+5491133430235");

        Assertions.assertTrue(true);

    }

    @Test
    @DisplayName("Prueba carga de usuarios nuevos.")
    public void cargarUsuariosTest() {
        TelegramBot telegramBot = new TelegramBot();
        telegramBot.registrarUsuarios();

        try {
            Thread.sleep(10000); //necesita este sleep para que cargue el bot
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assertions.assertTrue(true);

    }
}
