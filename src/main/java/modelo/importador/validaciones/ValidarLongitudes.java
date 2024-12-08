package modelo.importador.validaciones;

public class ValidarLongitudes {
    public static boolean validarLongitudes(String tipoDoc, String nroDocumento, String nombre, String apellido, String mail, String fechaColab, String formaColaboracion, String fechaCaducidad, String fechaDonacion, String entregada, String motivoDistribucion, String tipoComida) {
        return tipoDoc.length() > 3 || nroDocumento.length() > 8 || nombre.length() > 255
                || apellido.length() > 255 || mail.length() > 255
                || fechaColab.length() > 10 || formaColaboracion.length() > 22
                || fechaCaducidad.length() > 10 || fechaDonacion.length() > 10
                || entregada.length() > 1 || motivoDistribucion.length() > 13
                || tipoComida.length() > 255;
    }

    //
}
