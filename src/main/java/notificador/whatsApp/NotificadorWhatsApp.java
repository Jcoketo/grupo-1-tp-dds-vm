
package notificador.whatsApp;


import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.math.BigDecimal;

public class NotificadorWhatsApp implements AdapterWhatsApp{
    // Find your Account Sid and Token at twilio.com/console
    public static final String ACCOUNT_SID = "ACceb26feb835bb4276370109913ad91ed";
    public static final String AUTH_TOKEN = "ae6555c43605e7325f342398ed2b6649";
    /*
    public static void main(String[] args) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new PhoneNumber("whatsapp:+5491133430235"),
                        new PhoneNumber("whatsapp:+14155238886"),
                        "Notificacion de suscripciones")
                .create();

        System.out.println(message.getSid());
    }
    */

    @Override
    public void enviarWhatsApp(String mensaje, String destinatario) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new PhoneNumber("whatsapp:"+destinatario),
                        new PhoneNumber("whatsapp:+14155238886"),
                        mensaje)
                .create();

        System.out.println(message.getSid());
    }
}