package recomendadorDePuntos;

import recomendadorDePuntos.apiMock.dtos.PuntoEstrategico;

import java.util.List;

public interface AdapterRecomendador {
    List<PuntoEstrategico> obtenerPuntosIdeales(Double latitud, Double longitud, Double radio);
}
