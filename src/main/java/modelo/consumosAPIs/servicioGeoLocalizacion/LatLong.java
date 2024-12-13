package modelo.consumosAPIs.servicioGeoLocalizacion;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LatLong {
    private Double latitud;
    private Double longitud;

    public LatLong(Double latitud, Double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

}
