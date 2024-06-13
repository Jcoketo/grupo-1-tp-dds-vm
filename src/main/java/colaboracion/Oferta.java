package colaboracion;

import lombok.Getter;
import personas.Rubro;

public class Oferta {
    private String nombre;
    private TipoOferta tipoOferta;
    private Rubro rubro;
    @Getter private Double puntosNecesarios;
    private String imagen;

    public Oferta(String nombre, Double puntosNecesarios) {
        this.nombre = nombre;
        this.puntosNecesarios = puntosNecesarios;
    }
}