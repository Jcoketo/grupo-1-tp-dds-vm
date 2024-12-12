package modelo.personas;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;

@Entity
public class PersonaHumana extends Persona{

    @Column
    @Getter @Setter private String nombre;

    @Column
    @Getter @Setter private String apellido;

    @Column
    @Getter @Setter private LocalDate fechaNacimiento;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "numeroDoc", column = @Column(name = "numero_doc")),
        @AttributeOverride(name = "tipoDoc", column = @Column(name = "tipo_doc", columnDefinition = "VARCHAR(255)"))
    })
    @Getter @Setter private Documento documento;

    // CONSTRUCTOR PRINCIPAL
    public PersonaHumana(TipoDocumento tipoDoc, String nroDoc, String nombre, String apellido, MedioDeContacto medioDeContacto, String direccion, String fechaNacimiento) {
        this.documento = new Documento(nroDoc,tipoDoc);
        this.nombre = nombre;
        this.apellido = apellido;
        this.mediosDeContacto = new ArrayList<MedioDeContacto>();
        this.mediosDeContacto.add(medioDeContacto);
        this.direccion = direccion;
        this.tipoPersona = TipoPersona.PH;
        this.fechaNacimiento = LocalDate.parse(fechaNacimiento);
    }

    // COSNTRUCTOR PARA IMPORTADOR CSV
    public PersonaHumana(TipoDocumento tipoDocumento, String nroDocumento, String nombre, String apellido, MedioDeContacto medioDeContacto) {
        this.mediosDeContacto = new ArrayList<MedioDeContacto>();
        this.mediosDeContacto.add(medioDeContacto);
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = new Documento(nroDocumento,tipoDocumento);
        this.tipoPersona = TipoPersona.PH;
    }

    public PersonaHumana() {

    }


}