package presentacion.vistasAdmin;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class SCVCargadoController implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {


        List<UploadedFile> uploadedFiles = context.uploadedFiles("file");

        if (!uploadedFiles.isEmpty()) {
            UploadedFile file = uploadedFiles.get(0);
            String fileName = file.filename();

            System.out.println("Received file: " + fileName);
            File archivo = new File("main/resources/SCVs/" + file.filename());
            try {
                FileUtils.copyInputStreamToFile(file.content(), archivo);
            } catch (Exception e) {

            }




        }
    }
}
