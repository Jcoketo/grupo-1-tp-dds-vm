package elementos;

import lombok.Getter;
import lombok.Setter;

public class PuntoEstrategico {
    @Getter private Double latitud;
    @Getter private Double longitud;
    @Getter @Setter private String direccion;

    public PuntoEstrategico(Double latitud, Double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion = null;
    }

}