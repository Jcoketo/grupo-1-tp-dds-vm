package modelo.elementos;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.ws.rs.DefaultValue;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_incidente")
public abstract class Incidente {

    @Id
    @GeneratedValue
    @Getter private int id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "heladera_id", nullable = false)
    @Getter protected Heladera heladera;

    @Column
    @Getter protected LocalDateTime fechaHoraIncidente;

    @Column
    @Getter
    @DefaultValue("0")
    protected Boolean estaSolucionado;


    public Incidente(Heladera heladera) {
        this.heladera = heladera;
        this.fechaHoraIncidente = LocalDateTime.now();
    }

    public Incidente() {

    }

    public void fueResuelto() {
        this.estaSolucionado = Boolean.TRUE;
    }

}
