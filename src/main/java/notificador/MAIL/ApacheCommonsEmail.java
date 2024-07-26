package notificador.MAIL;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import static importador.EscribirLogError.escribirMensajeError;

public class ApacheCommonsEmail implements AdapterMAIL{
    private String hostName = "smtp.gmail.com";
    private int smtpPort = 587;
    private String username = "jmenazzibaldini@frba.utn.edu.ar";
    private String password = "";

    // contra : cjncgybotmgefepg

    @Override
    public void enviarMAIL(String mensaje, String destinatario, String asunto) {
        try{
            Email email = new SimpleEmail();
            email.setHostName(this.hostName);
            email.setSmtpPort(this.smtpPort);
            email.setAuthenticator(new DefaultAuthenticator(this.username, this.password));
            email.setSSLOnConnect(true);
            email.setFrom("jmenazzibaldini@frba.utn.edu.ar");
            email.setSubject(asunto);
            email.setMsg(mensaje);
            email.addTo(destinatario);
            email.send();
            System.out.println("Se envió el mensaje: "+mensaje);
            System.out.println("Al mail: "+ destinatario);
        } catch (EmailException e){
            String mensajeError = "Error al enviar mail a " + destinatario + ": " + e.getMessage();
            escribirMensajeError(mensajeError);

        }
    }
}
