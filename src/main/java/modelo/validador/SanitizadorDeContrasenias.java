package modelo.validador;

public class SanitizadorDeContrasenias {
    public static String eliminarMultiplesEspacios(String contrasenia) {
        while (contrasenia.contains("  ")) { // 2 spaces
            contrasenia = contrasenia.trim();
            contrasenia = contrasenia.replaceAll("  ", " "); // (2 spaces, 1 space)
        }
        return contrasenia;
    }
}