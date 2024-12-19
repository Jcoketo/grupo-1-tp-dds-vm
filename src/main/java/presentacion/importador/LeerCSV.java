package presentacion.importador;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.importador.CargarCSV;
import modelo.importador.ProcesarCSV;
import modelo.importador.RegistroLeido;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LeerCSV implements Handler {

    @Override
    public void handle(@NotNull Context context) throws Exception {

        String filename = context.sessionAttribute("fileName");
        List<RegistroLeido> registrosLeidos = CargarCSV.cargarCSV(filename);
        ProcesarCSV.procesarCSV(registrosLeidos);
        context.consumeSessionAttribute("fileName");

        context.sessionAttribute("cargaMasiva", "El archivo se ha cargado exitosamente!");
        context.redirect("/cargarCSV");

    }
}
