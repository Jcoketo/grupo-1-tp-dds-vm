package notificador.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TelegramBot extends TelegramLongPollingBot {




    public static void main(String[] args) {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new TelegramBot());

            //TelegramBot telegramBot = new TelegramBot();

            //telegramBot.enviarMensaje("Hola", "6244017669");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            String userFirstName = update.getMessage().getChat().getFirstName();

            switch(messageText) {
                case "/start":
                    startCommandReceived(chatId, userFirstName);
                    //ACA REGISTAR EL CHATID
                    break;
                default:
                    sendResponseMessage(chatId, "Sorry, command not recognized");
            }
        }
    }

    public void registrarUsuarios(){
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new TelegramBot());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startCommandReceived(long chatId, String userName) {
        String mensaje = ("Hola, " + userName + "!\nYa lo registramos en el sistema de notificaciones.");
        enviarMensaje(mensaje, String.valueOf(chatId));
    }

    private void sendResponseMessage(long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void enviarMensaje(String mensaje, String chatId){
        SendMessage sendMessage = new SendMessage(chatId, mensaje);
        try {
            execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SendMessage generateSendMessage(){return new SendMessage("6244017669", "HolAAAA");}

    @Override
    public String getBotUsername() {
        return "CSS108Notificador_utn_bot";
    }

    @Override
    public String getBotToken() {
        return "7488442886:AAFwLufU8QC85lVsKUq-24ywnAZWJlmiEFA";
    }
}