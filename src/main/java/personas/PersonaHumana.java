package personas;

import colaboracion.Colaboracion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PersonaHumana extends Colaborador{
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private List<Colaboracion> colaboracionesPosibles;
    private Documento documento;

    // En el Constructor deben ir solo los datos obligatorios! Hay que arreglarlo.
    public PersonaHumana(String nombre, String apellido, String email, String nroDocumento, TipoDocumento tipoDocumento, List<MedioDeContacto> mediosDeContacto, String direccion) {
        super(mediosDeContacto, direccion);
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = new Documento(nroDocumento,tipoDocumento);
        this.tipo = TipoPersona.PH; // Obligatorio
    }

    public void colaborar(Colaboracion colaboracion) {
        this.colaboracionesPosibles.add(colaboracion);
        //TODO
    }
}
