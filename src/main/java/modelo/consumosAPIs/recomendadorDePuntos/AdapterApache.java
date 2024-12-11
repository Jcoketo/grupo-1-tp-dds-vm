package modelo.consumosAPIs.recomendadorDePuntos;

import modelo.elementos.PuntoEstrategico;
import modelo.consumosAPIs.recomendadorDePuntos.apiMock.ApiMockCall;
import modelo.consumosAPIs.recomendadorDePuntos.apiMock.dtos.PuntoDeColocacion;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdapterApache implements AdapterRecomendador{
    private ApiMockCall servicioApache = ApiMockCall.getInstancia();

    @SneakyThrows // Esto es para catchear la Exception.
    public List<PuntoEstrategico> obtenerPuntosDeColocacion(Double latitud, Double longitud, Double radio) {
        PuntoDeColocacion[] puntos = servicioApache.obtenerPuntosDeColocacion(latitud, longitud, radio);
        List<PuntoDeColocacion> puntosDeColocacion = Arrays.stream(puntos).toList();

        return this.convertirEnPuntosEstrategicos(puntosDeColocacion);
    };

    private List<PuntoEstrategico> convertirEnPuntosEstrategicos (List<PuntoDeColocacion> puntosDeColocacion) {
        List<PuntoEstrategico> puntosEstrategicos = new ArrayList<PuntoEstrategico>();
        for(PuntoDeColocacion punto : puntosDeColocacion) {
            PuntoEstrategico puntoEstrategico = new PuntoEstrategico(punto.getLatitud(), punto.getLongitud());
            puntosEstrategicos.add(puntoEstrategico);
        }

        return puntosEstrategicos;
    };
}