package personas;

import colaboraciones.Colaboracion;
import enums.MedioContacto;

import java.time.LocalDateTime;
import java.util.List;

public class PersonaHumana extends Colaborador{
    private String nombre;
    private String apellido;
    private LocalDateTime fechaNacimiento;


    public PersonaHumana(List<MedioContacto> contacto, String direccion, String nombre, String apellido, LocalDateTime fechaNacimiento) {
        super(contacto, direccion);
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
    }

    public void colaborar(Colaboracion colaboracion) {
        colaboraciones.add(colaboracion);
        //TODO
    }
}
