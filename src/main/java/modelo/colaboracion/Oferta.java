package modelo.colaboracion;

import lombok.Getter;
import lombok.Setter;
import modelo.personas.Rubro;

import javax.persistence.*;

@Entity
@Table(name = "oferta")
public class Oferta {

    @Id
    @GeneratedValue
    @Getter private int id;

    @Column
    @Getter private String nombre;

    @Column(length = 500)
    @Getter private String descripcion;

    @Enumerated(EnumType.STRING)
    @Getter private TipoOferta tipoOferta;

    @Enumerated(EnumType.STRING)
    @Getter private Rubro rubro;

    @Column
    @Getter @Setter
    private boolean disponibilidad;

    @Column
    @Getter private Double puntosNecesarios;

    @Column
    @Getter private String imagen;

    public Oferta(String nombre, String descripcion, TipoOferta tipoOferta, Rubro rubro, Boolean disponibilidad, Double puntosNecesarios, String imagen) {
        this.nombre = nombre;
        this.puntosNecesarios = puntosNecesarios;
        this.descripcion = descripcion;
        this.tipoOferta = tipoOferta;
        this.rubro = rubro;
        this.disponibilidad = disponibilidad;
        this.imagen = imagen;
        this.disponibilidad = true;
    }

    public Oferta() {

    }

}