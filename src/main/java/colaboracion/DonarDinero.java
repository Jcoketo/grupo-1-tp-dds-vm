package colaboracion;

import personas.TipoPersona;
import lombok.Getter;
import personas.Colaborador;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

public class DonarDinero extends Colaboracion{
    private LocalDate fechaDonacion;
    @Getter private Double monto;
    private FrecuenciaDonacion frecuenciaDonacion;

    public DonarDinero(Double monto, LocalDateTime fechaDonacion, FrecuenciaDonacion frecuenciaDonacion) {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PJ, TipoPersona.PH);
        this.monto = monto;
        //this.fechaDonacion = fechaDonacion;
        this.frecuenciaDonacion = frecuenciaDonacion;

        //this.persona = persona;
    }

    public DonarDinero(TipoPersona persona, Double monto, LocalDateTime fechaDonacion) {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PJ, TipoPersona.PH);
        this.monto = monto;
        //this.fechaDonacion = fechaDonacion;

        //super.personasHabilitadas.add(TipoPersona.PH);
        //super.personasHabilitadas.add(TipoPersona.PJ);

        //this.persona = persona;
    }

    @Override
    public void hacerColaboracion(Colaborador colaborador) {
        //TODO
    }

    @Override
    public boolean validar(Colaborador colaborador){
        //TODO
        return true;
    }

    @Override
    public void incrementarPuntos(Colaborador colaborador){
        //TODO
    }

    /*
    public String getTipo() {
        return "DONAR_DINERO";
    }*/
}
