package colaboracion;

import personas.TipoPersona;
import lombok.Getter;
import personas.Colaborador;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

public class DonarDinero extends Colaboracion{
    @Getter private Double monto;
    private FrecuenciaDonacion frecuenciaDonacion;

    {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PJ, TipoPersona.PH);
    }

    public DonarDinero(Double monto, LocalDateTime fechaDonacion, FrecuenciaDonacion frecuenciaDonacion) {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PJ, TipoPersona.PH);
        this.monto = monto;
        this.frecuenciaDonacion = frecuenciaDonacion;

        //this.persona = persona;
    }
    // CONSTRUCTOR PARA EL IMPORTDOR DE SCV
    public DonarDinero(Double monto, LocalDateTime fechaDonacion) {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PJ, TipoPersona.PH);
        this.monto = monto;
        this.fechaDonacion = fechaDonacion;
        this.frecuenciaDonacion = FrecuenciaDonacion.UNICA; // es unica? TODO

    }

    @Override
    public void incrementarPuntos(Colaborador colaborador){ colaborador.incrementarPuntaje((float) (monto*0.5));}

    @Override
    public void hacerColaboracion(Colaborador colaborador) {
        incrementarPuntos(colaborador);
        //TODO
    }

    @Override
    public boolean validar(Colaborador colaborador){
        return this.tiposPersonasHabilitadas.contains(colaborador.getTipo());
    }

    /*
    public String getTipo() {
        return "DONAR_DINERO";
    }*/
}
