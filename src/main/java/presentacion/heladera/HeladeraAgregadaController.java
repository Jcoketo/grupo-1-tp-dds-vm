package presentacion.heladera;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.authService.AuthServiceColaboracion;
import modelo.excepciones.ExcepcionValidacion;
import modelo.personas.TipoDocumento;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioHeladeras;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HeladeraAgregadaController implements Handler {
    private RepositorioHeladeras repoHeladeras;

    public HeladeraAgregadaController(RepositorioHeladeras repoHeladeras) {
        super();
        this.repoHeladeras = repoHeladeras;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = new HashMap<>();
        String nombre = context.formParam("nombre");
        LocalDate fechaInicio = LocalDate.parse(context.formParam("fecha-inicio")); // falta parsear a LocalDate
        Integer capacidad = Integer.parseInt(Objects.requireNonNull(context.formParam("capacidad")));
        Boolean activa = Boolean.valueOf(context.formParam("activa"));
        String direccion = context.formParam("ubicacion");


        if (nombre.equals("") || direccion.equals(""))  {
            model.put("error", "Debe completar los campos obligatorios");
            //context.status(400);
            context.redirect("/crearCuentaFisica");
            return;
        }

        Boolean activo = null;
        if (activa) {
            activo = Boolean.FALSE;
        } else if (!(activa)) {
            activo = Boolean.TRUE;
        }

        try{
            Integer idPersona = context.sessionAttribute("idPersona");
            AuthServiceColaboracion.registrarColaboracionHeladera(idPersona, nombre, fechaInicio, capacidad, activo, direccion);
        }
        catch (ExcepcionValidacion e){
            model.put("errorHeladera", e.getMessage());
            //context.status(400);
            context.redirect("/agregarHeladera");
            return;
        }

        context.redirect("/aceptarAgregarHeladera");
    }
}
