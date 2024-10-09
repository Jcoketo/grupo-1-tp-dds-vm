package modelo.elementos;

import java.time.LocalDateTime;

public class UsoTarjetaPlastica {
    private Heladera heladera;
    private LocalDateTime fechaYHora;

    public UsoTarjetaPlastica(Heladera heladera) {
        this.heladera = heladera;
        this.fechaYHora = LocalDateTime.now();
    }
}