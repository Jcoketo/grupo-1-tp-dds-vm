package modelo.colaboracion;

import modelo.personas.TipoPersona;
import modelo.personas.Colaborador;
import persistencia.EntidadPersistente;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table
public abstract class Colaboracion extends EntidadPersistente {

    @Transient
    protected List<TipoPersona> tiposPersonasHabilitadas;

    @Column
    protected LocalDate fechaColaboracion;

    public abstract void hacerColaboracion(Colaborador colaborador);

    public abstract String validar(Colaborador colaborador);

    public abstract void incrementarPuntos(Colaborador colaborador);

}