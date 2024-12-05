package modelo.elementos;

import lombok.Getter;
import modelo.personas.Colaborador;
import modelo.personas.PersonaVulnerable;

import javax.persistence.*;

@Entity
public class TarjetaPlastica extends Tarjeta {
    @Column
    @Getter private int usosDisponibles;
    @Column
    @Getter private int usosConsumidos;
    @OneToOne
    @JoinColumn(name = "persona_vulnerable_id", referencedColumnName = "id")
    @Getter
    private PersonaVulnerable asociado;

    public TarjetaPlastica(PersonaVulnerable asociado) {
        super();
        this.usosDisponibles = 0;
        this.usosConsumidos = 0;
        this.asociado = asociado;
    }

    public TarjetaPlastica(String nro_tarjeta) {
        super(nro_tarjeta);
        this.usosDisponibles = 0;
        this.usosConsumidos = 0;
    }

    public TarjetaPlastica() {
        this.usosDisponibles = 0;
        this.usosConsumidos = 0;

    }


    @Override
    public void registrarUso(Heladera heladera) {
        super.registrarUso(heladera);
        this.usosConsumidos += 1;
    }

    public void asociarAPersonaVulnerable(PersonaVulnerable personaVulnerable) {
        this.asociado = personaVulnerable;
        this.usosDisponibles = (personaVulnerable.getMenoresACargo() * 2) + 4 ;
        this.fueRecibida();
    }

}