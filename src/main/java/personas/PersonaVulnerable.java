package personas;

import java.time.LocalDate;

import elementos.TarjetaPlastica;
import enums.TipoPersona;

public class PersonaVulnerable {
    private String nombre;
    private String domicilio;
    private String nroDocumento;
    private int menoresACargo;
    private LocalDate fechaNacimiento;
    private LocalDate fechaRegistro;
    private TarjetaPlastica tarjeta;
    public static final TipoPersona PersonaHumana = TipoPersona.PH;

    // Constructor, getters y setters
}