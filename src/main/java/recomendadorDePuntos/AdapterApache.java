package recomendadorDePuntos;

import recomendadorDePuntos.apiMock.ApiMockCall;
import recomendadorDePuntos.apiMock.dtos.PuntoEstrategico;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.List;

public class AdapterApache implements AdapterRecomendador{
    private ApiMockCall servicioApache = ApiMockCall.getInstancia();

    @SneakyThrows // Esto es para catchear la Exception.
    public List<PuntoEstrategico> obtenerPuntosIdeales(Double latitud, Double longitud, Double radio) {
        PuntoEstrategico[] puntosEstrategicos = servicioApache.obtenerPuntosEstrategicos(latitud, longitud, radio);
        return Arrays.stream(puntosEstrategicos).toList(); // Chequear esto.
    };
}