package presentacion.vistaAdmin;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;
import modelo.importador.CargarCSV;
import modelo.importador.ProcesarCSV;
import modelo.importador.RegistroLeido;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import utils.GeneradorModel;

import java.io.File;
import java.util.List;
import java.util.Map;

public class CSVCargadoController implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        List<UploadedFile> uploadedFiles = context.uploadedFiles("file");

        if (uploadedFiles.isEmpty() || uploadedFiles == null) {
            context.sessionAttribute("errorCargaMasiva", "No se ha cargado ningun archivo");
            context.redirect("/cargarCSV");
            return;
        }

        UploadedFile file = uploadedFiles.get(0);
        String fileName = file.filename();
        System.out.println("Received file: " + fileName);
        File archivo = new File("main/resources/archivos/CSVs/" + file.filename());
        try {
            FileUtils.copyInputStreamToFile(file.content(), archivo);
            List<RegistroLeido> registrosLeidos = CargarCSV.CargarSCV(fileName);
            ProcesarCSV.ProcesarCSV(registrosLeidos);
            context.redirect("/cargarCSV");
            //Hasta este punto esta cargado el archivp, quedaria cargarlo a la base de datos

        } catch (Exception e) {
            context.sessionAttribute("errorCargaMasiva", "Hubo un error al cargar y/o procesar el archivo. Por favor, intente nuevamente.");
            context.redirect("/cargarCSV");
        }
    }
}
