package modelo.consumosAPIs.recomendadorDePuntos;

import modelo.elementos.PuntoEstrategico;

import java.util.List;

public interface AdapterRecomendador {
    List<PuntoEstrategico> obtenerPuntosDeColocacion(Double latitud, Double longitud, Double radio);
}
