package modelo.recomendadorDePuntos.apiMock.dtos;

import lombok.Getter;

public class PuntoDeColocacion {
    @Getter private Double latitud;
    @Getter private Double longitud;

    public Double getLatitud() {
        return this.latitud;
    }

    public Double getLongitud() {
        return this.longitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }
}