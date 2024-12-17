package modelo.personas;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Setter;
import modelo.elementos.TarjetaPlastica;
import lombok.Getter;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;

@Entity
@Table
public class PersonaVulnerable {

    @Id
    @GeneratedValue
    @Getter private int id;
    
    @Column
    @Getter private LocalDate fechaRegistro;

    @Column
    @Getter private String nombreApellido;

    @Column
    @Getter private Integer menoresACargo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tarjeta_id", referencedColumnName = "id")
    @Getter private TarjetaPlastica tarjeta;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "persona_alta_id", referencedColumnName = "id")
    @Getter private PersonaHumana dioAlta;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "numeroDoc", column = @Column(name = "numero_doc")),
            @AttributeOverride(name = "tipoDoc", column = @Column(name = "tipo_doc", columnDefinition = "VARCHAR(255)"))
    })
    @Getter @Setter
    private Documento documento;

    @Column
    @Getter protected String direccion;

    @Column
    @Getter private LocalDate fechaNacimiento;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "persona_humana_id", referencedColumnName = "id")
//    @Getter private PersonaHumana persona;
// persona, nombre, tipoDoc, numeroDocumento, domicilio, nroTarjeta, cantidadMenores

    public PersonaVulnerable(PersonaHumana persona, String nombreApellido,TipoDocumento tipoDocumento, String numeroDocumento, String domicilio, TarjetaPlastica tarjeta, Integer menoresACargo, LocalDate fechaNacimiento) {
        this.dioAlta = persona;
        this.nombreApellido = nombreApellido;
        if ( !(tipoDocumento == null) && !(numeroDocumento == null) ) {
            this.documento = new Documento(numeroDocumento, tipoDocumento);
        }
        this.direccion = domicilio;
        this.menoresACargo = menoresACargo;
        this.tarjeta = tarjeta;
        this.fechaRegistro = LocalDate.now();
        this.fechaNacimiento = fechaNacimiento;
    }

    public PersonaVulnerable() {

    }
}
