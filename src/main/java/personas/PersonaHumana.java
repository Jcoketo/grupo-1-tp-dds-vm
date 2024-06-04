package personas;

import colaboracion.Colaboracion;

import java.time.LocalDateTime;
import java.util.List;

public class PersonaHumana extends Colaborador{
    private String nombre;
    private String apellido;
    private LocalDateTime fechaNacimiento;
    private String nroDocumento;
    private String tipoDocumento;


    // Constructor para importacion
    public PersonaHumana(String nombre, String apellido, String email, String nroDocumento, String tipoDocumento) {
        super(email);
        this.nombre = nombre;
        this.apellido = apellido;
        this.nroDocumento = nroDocumento;
        this.tipoDocumento = tipoDocumento;
    }

    public PersonaHumana(List<MedioContacto> contacto, String direccion, String nombre, String apellido, LocalDateTime fechaNacimiento, String email) {
        super(contacto, direccion, email);
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
    }

    public void colaborar(Colaboracion colaboracion) {
        colaboraciones.add(colaboracion);
        //TODO
    }

    public TipoPersona getTipo() {
        return TipoPersona.PH;
    }
}
