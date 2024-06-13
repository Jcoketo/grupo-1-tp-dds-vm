package elementos;

import java.time.LocalDateTime;

public class UsoTarjetaPlastica {
    private Heladera heladera;
    private LocalDateTime fechaYHora;

    public UsoTarjetaPlastica(Heladera heladera, LocalDateTime fechaYHora) {
        this.heladera = heladera;
        this.fechaYHora = fechaYHora;
    }
}
