package colaboracion;

import personas.TipoPersona;
import personas.Colaborador;

import java.util.List;

public abstract class Colaboracion {
    protected List<TipoPersona> tiposPersonasHabilitadas;
    protected int coeficiente;

    public abstract void hacerColaboracion(Colaborador colaborador);

    public abstract boolean validar(Colaborador colaborador);

    public abstract void incrementarPuntos(Colaborador colaborador);

    /*
    public Boolean puedeHacerColaboracion(TipoPersona tipoDePersona) throws Exception {
        if (!tiposPersonasHabilitadas.contains(tipoDePersona)) {
            throw new Exception("Tipo de persona no habilitada para esta colaboración");
        }
        return true;
    }*/
}
