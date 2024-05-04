package Notificador.MAIL;

public class ApacheCommonsEmail implements AdapterMAIL{
    private String hostName = "smtp-relay.sendinblue.com";
    private int smtpPort = 587;
    private String username = "sistemaserviciosddsutn@gmail.com";
    private String password = "AYEGZh6tCRKfg3Tp";

    @Override
    public void enviarMAIL(String mensaje, String destinatario) {
        try{
            Email email = new SimpleEmail();
            email.setHostName(this.hostName);
            email.setSmtpPort(this.smtpPort);
            email.setAuthenticator(new DefaultAuthenticator(this.username, this.password));
            email.setSSLOnConnect(true);
            email.setFrom("sistemaserviciosddsutn@gmail.com");
            email.setSubject("Email de prueba");
            email.setMsg(mensaje);
            email.addTo(destinatario);
            email.send();
            System.out.println("Se envió el mensaje: "+mensaje);
            System.out.println("Al mail: "+ destinatario);
        } catch (EmailException e){
            System.out.println("Ocurrió un error al enviar el mail.");
        }
    }
}
