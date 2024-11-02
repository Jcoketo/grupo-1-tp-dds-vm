package modelo.elementos;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Incidente {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "heladera_id", nullable = false)
    @Getter protected Heladera heladera;

    @Column
    protected LocalDateTime fechaHoraIncidente;

    public Incidente(Heladera heladera) {
        this.heladera = heladera;
        this.fechaHoraIncidente = LocalDateTime.now();
    }

}
