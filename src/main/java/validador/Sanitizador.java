package validador;

public class Sanitizador {
    public static String borrarEspacios(String contrasenia) {
        while (contrasenia.contains("  ")) { // 2 spaces
            contrasenia = contrasenia.trim();
            contrasenia = contrasenia.replaceAll("  ", " "); // (2 spaces, 1 space)
        }
        return contrasenia;
    }
}
