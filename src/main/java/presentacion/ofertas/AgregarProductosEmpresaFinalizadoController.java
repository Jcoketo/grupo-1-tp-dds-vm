package presentacion.ofertas;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;
import modelo.colaboracion.Oferta;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioIncidentes;
import persistencia.RepositorioOfertas;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class AgregarProductosEmpresaFinalizadoController implements Handler {

    private RepositorioOfertas repoOfertas;

    public AgregarProductosEmpresaFinalizadoController(RepositorioOfertas repoOfertas) {
        this.repoOfertas = repoOfertas;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {

        String nombre = Objects.requireNonNull(context.formParam("nombre"));
        String tipo = Objects.requireNonNull(context.formParam("tipo"));
        String descripcion = Objects.requireNonNull(context.formParam("descripcion"));
        Integer puntos = Integer.parseInt(Objects.requireNonNull(context.formParam("puntos")));

        context.redirect("/aceptarAgregarProducto");
    }
}

