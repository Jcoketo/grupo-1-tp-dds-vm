package colaboracion;

import personas.Rubro;

public class Oferta {
    private String nombre;
    private TipoOferta tipoOferta;
    private Rubro rubro;
    private Float puntosNecesarios;
    private String imagen;

    public Oferta(String nombre, Float puntosNecesarios) {
        this.nombre = nombre;
        this.puntosNecesarios = puntosNecesarios;
    }
}