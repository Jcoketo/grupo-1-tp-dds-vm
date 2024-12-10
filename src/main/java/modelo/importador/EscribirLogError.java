package modelo.importador;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class EscribirLogError {
    public static void escribirMensajeError(String mensajeError, String filename) {
        String nombreArchivo = "src/main/resources/archivos/CSVs/erroresCSVs/" + filename + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo, true))) {
            writer.write(mensajeError + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void crearArchivoLog(String nombreArchivo) {
        String sanitizedFileName = nombreArchivo.replaceAll("[\\\\/:*?\"<>|]", "_");
        String nombreTXT = "src/main/resources/archivos/CSVs/erroresCSVs/" + sanitizedFileName + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreTXT))) {
            writer.write("Log de Errores\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void escribirMensajeErrorXMail(String mensajeError) {
        String nombreArchivo = "src/main/resources/archivos/erroresMAIL.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo, true))) {
            writer.write(mensajeError + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
