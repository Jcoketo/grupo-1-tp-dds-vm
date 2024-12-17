package modelo.consumosAPIs.recomendadorDePuntos.apiMock.dtos;

import lombok.Getter;

public class PuntoDeColocacion {
    @Getter private Double latitud;
    @Getter private Double longitud;
    @Getter private String direccion;
    @Getter private String area;

    public Double getLatitud() {
        return this.latitud;
    }

    public Double getLongitud() {
        return this.longitud;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public String getArea() {
        return this.area;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setArea(String area) {
        this.area = area;
    }
}