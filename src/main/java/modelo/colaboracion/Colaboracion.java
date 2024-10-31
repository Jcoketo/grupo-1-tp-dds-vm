package modelo.colaboracion;

import modelo.personas.TipoPersona;
import modelo.personas.Colaborador;
import persistencia.EntidadPersistente;

import java.time.LocalDate;
import java.util.List;



public abstract class Colaboracion  {
    protected List<TipoPersona> tiposPersonasHabilitadas;
    protected LocalDate fechaColaboracion;

    public abstract void hacerColaboracion(Colaborador colaborador);

    public abstract String validar(Colaborador colaborador);

    public abstract void incrementarPuntos(Colaborador colaborador);

}