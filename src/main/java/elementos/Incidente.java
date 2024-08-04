package elementos;

import java.time.LocalDateTime;
import lombok.Getter;

public abstract class Incidente {

    @Getter protected Heladera heladera;
    protected LocalDateTime fechaHoraIncidente;

    public Incidente(Heladera heladera) {
        this.heladera = heladera;
        this.fechaHoraIncidente = LocalDateTime.now();
    }

}
