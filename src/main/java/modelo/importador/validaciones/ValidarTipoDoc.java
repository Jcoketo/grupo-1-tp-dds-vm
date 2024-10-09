package modelo.importador.validaciones;

import modelo.personas.TipoDocumento;

public class ValidarTipoDoc {
    public static TipoDocumento validarTipoDocumento(String tipoDoc) {
        return switch (tipoDoc) {
            case "DNI" -> TipoDocumento.DNI;
            case "LC" -> TipoDocumento.LC;
            case "LE" -> TipoDocumento.LE;
            default -> null;
        };
    }
}
