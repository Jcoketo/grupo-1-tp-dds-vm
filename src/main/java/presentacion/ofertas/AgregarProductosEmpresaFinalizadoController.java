package presentacion.ofertas;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioIncidentes;
import persistencia.RepositorioOfertas;

import java.util.Objects;

public class AgregarProductosEmpresaFinalizadoController implements Handler {

    private RepositorioOfertas repoOfertas;

    public AgregarProductosEmpresaFinalizadoController(RepositorioOfertas reposiOfertas) {
        super();
        this.repoOfertas = reposiOfertas;
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
