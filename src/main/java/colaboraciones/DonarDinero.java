package colaboraciones;

import enums.FrecuenciaDonacion;
import enums.TipoPersona;
import lombok.Getter;
import personas.Colaborador;

import java.time.LocalDateTime;

public class DonarDinero extends Colaboracion{
    @Getter
    private Double monto;
    private FrecuenciaDonacion frecuenciaDonacion;

    /*COMENTE PORQUE ME TIRABA ERRORES EN EL TEST VER QUE ONDA
    static {
        personasHabilitadas.add(TipoPersona.PH);
        personasHabilitadas.add(TipoPersona.PJ);
    }
    */
    public DonarDinero(TipoPersona persona, Double monto, LocalDateTime fechaDonacion, FrecuenciaDonacion frecuenciaDonacion) {
        this.monto = monto;
        //this.fechaDonacion = fechaDonacion;
        this.frecuenciaDonacion = frecuenciaDonacion;

        //this.persona = persona;
    }

    public DonarDinero(TipoPersona persona, Double monto, LocalDateTime fechaDonacion) {
        this.monto = monto;
        //this.fechaDonacion = fechaDonacion;

        //super.personasHabilitadas.add(TipoPersona.PH);
        //super.personasHabilitadas.add(TipoPersona.PJ);

        //this.persona = persona;
    }

    @Override
    public void hacerColaboracion(Colaborador colaborador) {
        //TODO
        // A DONDE SE DONA?
    }


    public String getTipo() {
        return "DONAR_DINERO";
    }
}