package recomendadorDePuntos;

import elementos.PuntoEstrategico;
import recomendadorDePuntos.apiMock.dtos.PuntoDeColocacion;

import java.util.List;

public interface AdapterRecomendador {
    List<PuntoEstrategico> obtenerPuntosDeColocacion(Double latitud, Double longitud, Double radio);
}
