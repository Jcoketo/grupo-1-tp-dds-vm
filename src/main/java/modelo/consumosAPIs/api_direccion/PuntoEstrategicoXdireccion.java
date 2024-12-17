package modelo.consumosAPIs.api_direccion;

import lombok.SneakyThrows;
import modelo.consumosAPIs.recomendadorDePuntos.AdapterRecomendador;
import modelo.consumosAPIs.recomendadorDePuntos.apiMock.ApiMockCall;
import modelo.consumosAPIs.recomendadorDePuntos.apiMock.dtos.PuntoDeColocacion;
import modelo.consumosAPIs.recomendadorDePuntos.apiMock.dtos.PuntoDireccion;
import modelo.elementos.PuntoEstrategico;
import modelo.excepciones.ExcepcionValidacion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PuntoEstrategicoXdireccion {
    private static PuntoEstrategicoXdireccion instancia;
    private ApiMockCall servicio = ApiMockCall.getInstancia();

    private PuntoEstrategicoXdireccion() {
        // Private constructor to prevent instantiation
    }

    public static synchronized PuntoEstrategicoXdireccion getInstancia() {
        if (instancia == null) {
            instancia = new PuntoEstrategicoXdireccion();
        }
        return instancia;
    }

    @SneakyThrows // Esto es para catchear la Exception.
    public PuntoEstrategico obtenerPuntoDeColocacion(String direccion) {
        PuntoDireccion[] puntos = servicio.obtenerPuntosXdireccion(direccion);

        if ( puntos.length == 0 ) {
            throw new ExcepcionValidacion("La direccion ingresada no es una direccion valida."); }

        PuntoDireccion puntoDeColocacion = Arrays.stream(puntos).toList().get(0);

        return this.convertirEnPuntoEstrategico(puntoDeColocacion);
    }

    private PuntoEstrategico convertirEnPuntoEstrategico(PuntoDireccion puntosDeColocacion) {
        PuntoEstrategico puntoEstrategico = new PuntoEstrategico(puntosDeColocacion.getLatitud(), puntosDeColocacion.getLongitud());
        return puntoEstrategico;
    }
}
