package modelo.importador;

import lombok.Getter;

public class RegistroLeido {
    @Getter
    private String tipoDoc;
    @Getter
    private String nroDocumento;
    @Getter
    private String nombre;
    @Getter
    private String apellido;
    @Getter
    private String mail;
    @Getter
    private String fechaDonacion;
    @Getter
    private String formaColaboracion;
    @Getter
    private String cantidad;
    @Getter
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