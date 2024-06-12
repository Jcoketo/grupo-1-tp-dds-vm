package recomendadorDePuntos;

import recomendadorDePuntos.apiMock.dtos.PuntoEstrategico;

import java.util.List;

public class RecomendadorDePuntos {
    private static RecomendadorDePuntos instancia = null;
    private AdapterApache adapter;

    public static RecomendadorDePuntos getInstancia(){
        if(instancia == null){
            instancia = new RecomendadorDePuntos();
        }
        return instancia;
    }

    private RecomendadorDePuntos(){
        adapter = new AdapterApache();
    }

    public List<PuntoEstrategico> obtenerPuntosIdeales(Double latitud, Double longitud, Double radio) {
        return adapter.obtenerPuntosIdeales(latitud, longitud, radio);
    }
}