package elementos;

import lombok.Getter;

import java.time.LocalDateTime;

public class Sensoreo {
    private LocalDateTime fechaYhora;
    @Getter private Float tempRegistrada;
}