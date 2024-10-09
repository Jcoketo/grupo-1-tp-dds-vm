package modelo.personas;

import lombok.Getter;

import java.time.LocalDate;

public class PersonaHumana extends Persona{
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    @Getter private Documento documento;


    // CONSTRUCTOR PRINCIPAL
    public PersonaHumana(String nombre, String apellido, MedioDeContacto medioDeContacto) {
        super(medioDeContacto);
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoPersona = TipoPersona.PH;
    }

    // COSNTRUCTOR PARA IMPORTADOR CSV
    public PersonaHumana(TipoDocumento tipoDocumento, String nroDocumento, String nombre, String apellido, MedioDeContacto medioDeContacto) {
        super(medioDeContacto);
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = new Documento(nroDocumento,tipoDocumento);
        this.tipoPersona = TipoPersona.PH;
    }

}