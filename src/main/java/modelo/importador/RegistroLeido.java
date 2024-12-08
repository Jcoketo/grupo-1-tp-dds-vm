package modelo.importador;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    private String fechaColab;
    @Getter
    private String formaColaboracion;
    @Getter
    private String monto;
    @Getter
    private String idHeladeraDestino;
    @Getter
    private String tipoComida;
    @Getter
    private String fechaCaducidad;
    @Getter
    private String fechaDonacion;
    @Getter
    private String entregada;
    @Getter
    private String calorias;
    @Getter
    private String peso;
    @Getter
    private String idHeladeraOrigen;
    @Getter
    private List<Integer> idViandas; // Representa [1,2,3,4]
    @Getter
    private String motivoDistribucion;
    @Getter
    private List<Integer> idTarjetas; // Representa [1,2,3,4]
    @Getter
    private String cantidadRepartida;
    @Getter
    private Integer record;

    // Constructor completo
    public RegistroLeido(String tipoDoc, String nroDocumento, String nombre, String apellido, String mail, String fechaColab, String formaColaboracion, String monto,
            String idHeladeraDestino, String tipoComida, String fechaCaducidad, String fechaDonacion, String entregada, String calorias, String peso,
            String idHeladeraOrigen, String idViandas, String motivoDistribucion, String idTarjetas, String cantidadRepartida, long recordNumber) {
        this.tipoDoc = tipoDoc;
        this.nroDocumento = nroDocumento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.fechaColab = fechaColab;
        this.formaColaboracion = formaColaboracion;
        this.monto = monto;
        this.idHeladeraDestino = idHeladeraDestino;
        this.tipoComida = tipoComida;
        this.fechaCaducidad = fechaCaducidad;
        this.fechaDonacion = fechaDonacion;
        this.entregada = entregada;
        this.calorias = calorias;
        this.peso = peso;
        this.idHeladeraOrigen = idHeladeraOrigen;
        this.idViandas = parseStringToList(idViandas);
        this.motivoDistribucion = motivoDistribucion;
        this.idTarjetas = parseStringToList(idTarjetas);
        this.cantidadRepartida = cantidadRepartida;
        this.record = Math.toIntExact(recordNumber);
    }

    public  List<Integer> parseStringToList(String input) {
        String cleanedInput = input.replaceAll("[\\[\\]]", "");
        return Arrays.stream(cleanedInput.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
