package modelo.elementos;

import modelo.personas.PersonaVulnerable;

public class TarjetaPlastica extends Tarjeta {
    private int usosDisponibles;
    private int usosConsumidos;
    private PersonaVulnerable asociado;

    public TarjetaPlastica(PersonaVulnerable asociado) {
        super();
        this.usosDisponibles = 4 + ( 2 * asociado.getMenoresACargo() );
        this.usosConsumidos = 0;
        this.asociado = asociado;

    }

    @Override
    public void registrarUso(Heladera heladera) {
        super.registrarUso(heladera);
        this.usosConsumidos += 1;
    }
}