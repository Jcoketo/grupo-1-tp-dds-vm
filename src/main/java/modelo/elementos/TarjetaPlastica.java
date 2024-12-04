package modelo.elementos;

import lombok.Getter;
import modelo.personas.PersonaVulnerable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

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
        this.usosDisponibles = 4 + ( 2 * asociado.getMenoresACargo() );
        this.usosConsumidos = 0;
        this.asociado = asociado;
    }

    public TarjetaPlastica() {

    }

    @Override
    public void registrarUso(Heladera heladera) {
        super.registrarUso(heladera);
        this.usosConsumidos += 1;
    }
}