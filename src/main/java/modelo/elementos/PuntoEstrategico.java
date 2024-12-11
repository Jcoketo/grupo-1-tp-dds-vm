package modelo.elementos;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "punto_estrategico")
public class PuntoEstrategico {


    @Id
    @GeneratedValue
    @Getter private Long id;

    @Column
    @Setter @Getter private Double latitud;
    @Column
    @Setter @Getter private Double longitud;
    @Column
    @Getter @Setter private String direccion;
    @Enumerated(EnumType.STRING)
    @Setter @Getter private Areas areas;

    public PuntoEstrategico(Double latitud, Double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion = null;
        this.areas = null;
    }

    public PuntoEstrategico(String direccion){
        this.latitud = 0.0;
        this.longitud = 0.0;
        this.direccion = direccion;
    }

    public PuntoEstrategico(String direccion, Double latitud, Double longitud){
       this.latitud = latitud;
       this.longitud = longitud;
       this.direccion = direccion;
    }


    public PuntoEstrategico() {

    }
}