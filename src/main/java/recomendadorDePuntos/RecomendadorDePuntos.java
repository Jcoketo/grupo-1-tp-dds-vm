package recomendadorDePuntos;

import elementos.PuntoEstrategico;
import recomendadorDePuntos.apiMock.dtos.PuntoDeColocacion;

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

    public List<PuntoEstrategico> obtenerPuntosRecomendados(Double latitud, Double longitud, Double radio) {
        return adapter.obtenerPuntosDeColocacion(latitud, longitud, radio);
    }
}