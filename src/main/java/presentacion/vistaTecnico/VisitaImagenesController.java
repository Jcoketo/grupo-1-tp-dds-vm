package presentacion.vistaTecnico;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class VisitaImagenesController implements Handler {

    @Override
    public void handle(Context ctx) throws Exception {
        String filePath = "src/main/resources/uploads/visitasImagenes/" + ctx.pathParam("filename");
        try {
            // Leer el archivo completo como un arreglo de bytes
            byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));

            if (fileBytes.length > 0) {
                ctx.contentType("image/*");
                ctx.result(fileBytes); // Enviar los bytes directamente
            } else {
                ctx.status(404).result("Imagen vacía");
            }
        } catch (IOException e) {
            e.printStackTrace();
            ctx.status(500).result("Error al cargar la imagen");
        }
    }
}
