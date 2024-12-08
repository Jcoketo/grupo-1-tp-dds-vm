package modelo.importador;

import lombok.Getter;

@Getter
public class RegistroLeido {

    private String tipoDoc;
    private String nroDocumento;
    private String nombre;
    private String apellido;
    private String mail;
    private String fechaDonacion;
    private String formaColaboracion;
    private String cantidad;
    private Integer record;

    public RegistroLeido(String tipoDoc, String nroDocumento, String nombre, String apellido, String mail, String fecha, String formaColaboracion, String cantidad, long recordNumber) {
        this.tipoDoc = tipoDoc;
        this.nroDocumento = nroDocumento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.fechaDonacion = fecha;
        this.formaColaboracion = formaColaboracion;
        this.cantidad = cantidad;
        this.record = Math.toIntExact(recordNumber);
    }


}
