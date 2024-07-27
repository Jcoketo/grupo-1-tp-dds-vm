package elementos;

import java.time.LocalDateTime;

public abstract class Incidente {

    protected Heladera heladera;
    protected LocalDateTime fechaHoraIncidente;

    public Incidente(Heladera heladera) {
        this.heladera = heladera;
        this.fechaHoraIncidente = LocalDateTime.now();
    }

    public Heladera getHeladera() {
        return heladera;
    }

}
