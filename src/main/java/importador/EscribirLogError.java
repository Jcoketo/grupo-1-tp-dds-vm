package importador;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class EscribirLogError {
        public static void escribirMensajeError(String mensajeError) {
            String nombreArchivo = "src/main/resources/logErroresCSV.txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo, true))) {
                writer.write(mensajeError + "\n");
            } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
