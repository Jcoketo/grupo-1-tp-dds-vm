package presentacion.vistasAdmin;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;
import modelo.importador.CargarCSV;
import modelo.importador.ProcesarCSV;
import modelo.importador.RegistroLeido;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SCVCargadoController implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = context.sessionAttribute("model");
        if (model == null) {
            model = new HashMap<>();
            context.sessionAttribute("model", model);
        }

        List<UploadedFile> uploadedFiles = context.uploadedFiles("file");

        if (uploadedFiles.isEmpty()) {
            model.put("errorLogin", "No se ha cargado ningun archivo");
            context.sessionAttribute("model", model);
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
            model.put("errorLogin", "Error en el CSV");
            context.sessionAttribute("model", model);
            context.redirect("/cargarCSV");
        }
    }
}
