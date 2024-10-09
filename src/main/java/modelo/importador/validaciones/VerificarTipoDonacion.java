package modelo.importador.validaciones;

public class VerificarTipoDonacion {
    public static boolean verificarTipoDonacion(String formaColaboracion) {
        return switch (formaColaboracion) {
            case "DINERO", "DONACION_VIANDAS", "REDISTRIBUCION_VIANDAS", "ENTREGA_TARJETAS" -> false;
            default -> true;
        };
    }
}
