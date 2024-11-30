package modelo.elementos;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Getter;

@Entity
@Table
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_incidente")
public abstract class Incidente {

    @Id
    @GeneratedValue
    @Getter private int id;

    @ManyToOne
    @JoinColumn(name = "heladera_id", nullable = false)
    @Getter protected Heladera heladera;

    @Column
    @Getter protected LocalDateTime fechaHoraIncidente;

    public Incidente(Heladera heladera) {
        this.heladera = heladera;
        this.fechaHoraIncidente = LocalDateTime.now();
    }

    public Incidente() {

    }



}
