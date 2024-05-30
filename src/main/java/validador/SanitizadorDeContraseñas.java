package validador;

public class SanitizadorDeContraseñas {
    public static String eliminarMultiplesEspacios(String contrasenia) {
        while (contrasenia.contains("  ")) { // 2 spaces
            contrasenia = contrasenia.trim();
            contrasenia = contrasenia.replaceAll("  ", " "); // (2 spaces, 1 space)
        }
        return contrasenia;
    }
}
// Esta clase tiene un método que se encarga de reemplazar los múltiples espacios contiguos por un solo espacio.