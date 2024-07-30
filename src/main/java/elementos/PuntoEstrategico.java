package elementos;

import lombok.Getter;
import lombok.Setter;

public class PuntoEstrategico {
    @Getter private Double latitud;
    @Getter private Double longitud;
    @Getter @Setter private String direccion;
    @Setter @Getter private Areas areas;

    public PuntoEstrategico(Double latitud, Double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion = null;
        this.areas = null;
    }


}