package modelo.personas;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;

@Entity
public class PersonaHumana extends Persona{

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private LocalDate fechaNacimiento;

    @OneToOne
    @JoinColumn(name = "documento_id", referencedColumnName = "id")
    @Setter
    @Getter private Documento documento;


    // CONSTRUCTOR PRINCIPAL
    public PersonaHumana(String nombre, String apellido, MedioDeContacto medioDeContacto) {
        this.mediosDeContacto = new ArrayList<MedioDeContacto>();
        this.mediosDeContacto.add(medioDeContacto);
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoPersona = TipoPersona.PH;
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

}