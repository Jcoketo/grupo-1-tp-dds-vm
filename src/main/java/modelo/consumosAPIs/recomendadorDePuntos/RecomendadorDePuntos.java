package modelo.consumosAPIs.recomendadorDePuntos;

import modelo.elementos.PuntoEstrategico;

import java.util.List;

public class RecomendadorDePuntos {
    private static RecomendadorDePuntos instancia = null;
    private final AdapterRecomendador adapter = new AdapterApache();

    public static RecomendadorDePuntos getInstancia(){
        if(instancia == null){
            instancia = new RecomendadorDePuntos();
        }
        return instancia;
    }

    public List<PuntoEstrategico> obtenerPuntosRecomendados(Double latitud, Double longitud, Double radio) {
        return adapter.obtenerPuntosDeColocacion(latitud, longitud, radio);
    }
}