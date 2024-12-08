package modelo.importador.validaciones;

public class ValidarLongitudes {
    public static boolean validarLongitudes(String tipoDoc, String nroDocumento, String nombre, String apellido, String mail, String fecha, String formaColaboracion, String cantidad) {
        return tipoDoc.length() > 3 || nroDocumento.length() > 10 || nombre.length() > 255
                || apellido.length() > 255 || mail.length() > 255
                || fecha.length() > 10 || formaColaboracion.length() > 22
                || cantidad.length() > 7;
    }

    //
}
