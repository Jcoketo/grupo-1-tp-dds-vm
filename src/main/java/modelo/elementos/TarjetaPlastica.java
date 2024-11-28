package modelo.elementos;

import modelo.personas.PersonaVulnerable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class TarjetaPlastica extends Tarjeta {
    @Column
    private int usosDisponibles;
    @Column
    private int usosConsumidos;
    @OneToOne
    @JoinColumn(name = "persona_vulnerable_id", referencedColumnName = "id")
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