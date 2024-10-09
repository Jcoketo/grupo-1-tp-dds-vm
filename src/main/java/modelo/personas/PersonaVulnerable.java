package modelo.personas;

import java.time.LocalDate;

import modelo.elementos.TarjetaPlastica;
import lombok.Getter;

public class PersonaVulnerable {
    private LocalDate fechaRegistro;
    @Getter private int menoresACargo;
    private TarjetaPlastica tarjeta;
    private PersonaHumana dioAlta;
    private PersonaHumana persona;
}