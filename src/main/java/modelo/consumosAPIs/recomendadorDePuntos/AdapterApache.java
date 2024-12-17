package modelo.consumosAPIs.recomendadorDePuntos;

import modelo.elementos.Areas;
import modelo.elementos.PuntoEstrategico;
import modelo.consumosAPIs.recomendadorDePuntos.apiMock.ApiMockCall;
import modelo.consumosAPIs.recomendadorDePuntos.apiMock.dtos.PuntoDeColocacion;
import lombok.SneakyThrows;

import java.awt.geom.Area;
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

            Areas area = deStringAarea(punto.getArea());
            PuntoEstrategico puntoEstrategico = new PuntoEstrategico(punto.getDireccion(),punto.getLatitud(), punto.getLongitud(),area);
            puntosEstrategicos.add(puntoEstrategico);
        }

        return puntosEstrategicos;
    };

    private Areas deStringAarea(String area){
        switch (area) {
            case "CABALLITO":
                return Areas.CABALLITO;
            case "PALERMO":
                return Areas.PALERMO;
            case "RECOLETA":
                return Areas.RECOLETA;
            case "BELGRANO":
                return Areas.BELGRANO;
            case "COLEGIALES":
                return Areas.COLEGIALES;
            case "VILLA_CRESPO":
                return Areas.VILLA_CRESPO;
            case "VILLA_URQUIZA":
                return Areas.VILLA_URQUIZA;
            case "VILLA_DEL_PARQUE":
                return Areas.VILLA_DEL_PARQUE;
            case "VILLA_LUGANO":
                return Areas.VILLA_LUGANO;
            case "VILLA_PUEYRREDON":
                return Areas.VILLA_PUEYRREDON;
            case "VILLA_REAL":
                return Areas.VILLA_REAL;
            case "VILLA_SANTA_RITA":
                return Areas.VILLA_SANTA_RITA;
            case "VILLA_SOLDATI":
                return Areas.VILLA_SOLDATI;
            case "VILLA_ORTUZAR":
                return Areas.VILLA_ORTUZAR;
            case "VILLA_DEVOTO":
                return Areas.VILLA_DEVOTO;
            case "VILLA_GRAL_MITRE":
                return Areas.VILLA_GRAL_MITRE;
            case "VILLA_LURO":
                return Areas.VILLA_LURO;
            case "VILLA_RIACHUELO":
                return Areas.VILLA_RIACHUELO;
            case "VILLA_SOCAYA":
                return Areas.VILLA_SOCAYA;
            default:
                return Areas.VILLA_CRESPO; // Valor por defecto
        }
    }
}