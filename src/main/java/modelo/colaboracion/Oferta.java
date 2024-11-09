package modelo.colaboracion;

import lombok.Getter;
import modelo.personas.Rubro;

import javax.persistence.*;


@Entity
@Table(name = "oferta")
public class Oferta {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    private TipoOferta tipoOferta;

    @Enumerated(EnumType.STRING)
    private Rubro rubro;

    @Column
    @Getter private Double puntosNecesarios;

    @Column
    private String imagen;

    public Oferta(String nombre, String descripcion, TipoOferta tipoOferta, Rubro rubro, Double puntosNecesarios, String imagen) {
        this.nombre = nombre;
        this.puntosNecesarios = puntosNecesarios;
        this.descripcion = descripcion;
        this.tipoOferta = tipoOferta;
        this.rubro = rubro;
        this.imagen = imagen;
    }

    public Oferta() {

    }

}