package colaboracion;

import personas.TipoPersona;
import personas.Colaborador;

import java.util.List;

public abstract class Colaboracion {
    protected static List<TipoPersona> personasHabilitadas;
    protected int coeficiente;

    public abstract void hacerColaboracion(Colaborador colaborador);

    public String getTipo() {
        return null;
    }

    public Double getMonto() {
        return null;
    }


    public Boolean puedeHacerColaboracion(TipoPersona persona) throws Exception {
        if (!personasHabilitadas.contains(persona)) {
            throw new Exception("Tipo de persona no habilitada para esta colaboración");
        }
        return true;
    }
}
