package modelo.consumosAPIs.recomendadorDePuntos.apiMock.dtos;

import lombok.Getter;

public class PuntoDireccion {
    @Getter private Double lat;
    @Getter private Double lon;

    public Double getLatitud() {
        return this.lat;
    }

    public Double getLongitud() {
        return this.lon;
    }

    public void setLatitud(Double latitud) {
        this.lat = latitud;
    }

    public void setLongitud(Double longitud) {
        this.lon = longitud;
    }
}
