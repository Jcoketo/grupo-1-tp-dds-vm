package modelo.colaboracion;

import lombok.Getter;
import lombok.Setter;
import modelo.personas.TipoPersona;
import modelo.personas.Colaborador;

import javax.persistence.*;
import javax.ws.rs.DefaultValue;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_colaboracion")
public abstract class Colaboracion {

    @Id
    @GeneratedValue
    @Getter
    private int id;

    @Transient
    protected List<TipoPersona> tiposPersonasHabilitadas;

    @Getter
    @Column
    protected LocalDate fechaColaboracion;

    @Column
    @Getter @Setter
    @DefaultValue("0")
    protected Boolean porCSV;

    public abstract void hacerColaboracion(Colaborador colaborador);

    public abstract String validar(Colaborador colaborador);

    public abstract void incrementarPuntos(Colaborador colaborador);

    public abstract Double conocerPuntaje();

    public abstract String getClassName();

}