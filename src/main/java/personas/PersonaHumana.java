package personas;

import colaboracion.Colaboracion;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PersonaHumana extends Colaborador{
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private List<Colaboracion> colaboracionesPosibles;

    @Getter private Documento documento;

    // En el Constructor deben ir solo los datos obligatorios! Hay que arreglarlo.
    public PersonaHumana(TipoDocumento tipoDocumento, String nroDocumento, String nombre, String apellido, String email, List<MedioDeContacto> mediosDeContacto) {
        super(mediosDeContacto);
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
