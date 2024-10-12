package presentacion;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class DonarDineroRealizadaController implements Handler {

    public DonarDineroRealizadaController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {

        Double monto = Double.parseDouble(Objects.requireNonNull(context.formParam("monto")));
        String nombre = context.formParam("nombre");
        Long numeroTarjeta = Long.parseLong(Objects.requireNonNull(context.formParam("numero-tarjeta")));
        String fechaVencimiento = context.formParam("fecha-expiracion");
        Integer codigoSeguridad = Integer.parseInt(Objects.requireNonNull(context.formParam("cvv")));

        // todo el chequeo de los datos de la tarjeta de credito
        // y descontar el dinero



        context.redirect("/graciasPorDonar");


    }
}
