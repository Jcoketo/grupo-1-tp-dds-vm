package colaboraciones;

import enums.TipoPersona;

import java.time.LocalDateTime;
import java.util.List;

public abstract class Colaboracion {
    protected static List<TipoPersona> personasHabilitadas;
    protected TipoPersona persona;
    protected LocalDateTime fechaDonacion;

    public abstract void hacerColaboracion();

    public String getTipo() {
        return null;
    }

    public Double getMonto() {
        return null;
    }


    public Boolean validarPersona(TipoPersona persona) throws Exception {
        if (!personasHabilitadas.contains(persona)) {
            throw new Exception("Tipo de persona no habilitada para esta colaboración");
        }
        return true;
    }
}
