package modelo.elementos;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "uso_tarjeta")
public class UsoTarjeta {

    @Id
    @GeneratedValue
    @Getter private long id;

    @OneToOne
    private Heladera heladera;

    @Column
    private LocalDateTime fechaYHora;

    public UsoTarjeta(Heladera heladera) {
        this.heladera = heladera;
        this.fechaYHora = LocalDateTime.now();
    }

    public UsoTarjeta() {

    }
}