package notificador.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TelegramBot extends TelegramLongPollingBot {

    public static void main(String[] args) {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new TelegramBot());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
// TODO Auto-generated method stub
        final String mensaje = update.getMessage().getText();

        System.out.println(mensaje);

        final String chatId = update.getMessage().getChat().getUserName();

        System.out.println(chatId);

    }

    private SendMessage generateSendMessage(Long chatId, int characterCount) {
        return new SendMessage(chatId.toString(), "The Message have " + characterCount + " characters");
    }

    @Override
    public String getBotUsername() {
// TODO Auto-generated method stub
        return "CSS108Notificador_utn_bot";
    }

    @Override
    public String getBotToken() {
// TODO Auto-generated method stub
        return "7488442886:AAFwLufU8QC85lVsKUq-24ywnAZWJlmiEFA";
    }
}