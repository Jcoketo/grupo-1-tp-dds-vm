package personas;

import colaboracion.Colaboracion;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class PersonaHumana extends Colaborador{
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private List<Colaboracion> colaboracionesPosibles;
    @Getter private Documento documento;

    // CONSTRUCTOR PRINCIPAL
    public PersonaHumana(String nombre, String apellido, MedioDeContacto medioDeContacto) {
        super(medioDeContacto);
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipo = TipoPersona.PH;
    }

    // COSNTRUCTOR PARA IMPORTADOR CSV
    public PersonaHumana(TipoDocumento tipoDocumento, String nroDocumento, String nombre, String apellido, MedioDeContacto medioDeContacto) {
        super(medioDeContacto);
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = new Documento(nroDocumento,tipoDocumento);
        this.tipo = TipoPersona.PH;
    }

}