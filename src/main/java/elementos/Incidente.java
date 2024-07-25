package elementos;

import java.time.LocalDateTime;

public abstract class Incidente {

    protected Heladera heladera;
    protected LocalDateTime fechaHoraIncidente;

    public Heladera getHeladera() {
        return heladera;
    }

}
