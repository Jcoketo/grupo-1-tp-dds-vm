package colaboracion;

import lombok.Getter;
import lombok.Setter;
import personas.TipoPersona;
import personas.Colaborador;

import java.time.LocalDate;
import java.util.Arrays;

public class DonarDinero extends Colaboracion{
    @Getter private Double monto;
    private FrecuenciaDonacion frecuencia;
    @Setter private static Double coeficiente = 1.5;

    // CONSTRUCTOR PRINCIPAL
    public DonarDinero(LocalDate fechaDonacion, Double monto, FrecuenciaDonacion frecuenciaDonacion) {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PJ, TipoPersona.PH);
        this.fechaColaboracion = fechaDonacion;
        this.monto = monto;
        this.frecuencia = frecuenciaDonacion;
    }

    // CONSTRUCTOR PARA IMPORTADOR CSV
    public DonarDinero(LocalDate fechaDonacion, Double monto) {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PJ, TipoPersona.PH);
        this.fechaColaboracion = fechaDonacion;
        this.monto = monto;
        this.frecuencia = FrecuenciaDonacion.UNICA; // Es unica? TODO
    }

    @Override
    public void hacerColaboracion(Colaborador colaborador) {
        String text = validar(colaborador);
        if(text == null){
            incrementarPuntos(colaborador);
            colaborador.agregarColaboracion(this);
        }
        else {
            System.out.println("Error!!!");
            System.out.println(text);
        }
    }

    @Override
    public String validar(Colaborador colaborador) {
        if(!this.tiposPersonasHabilitadas.contains(colaborador.getTipoPersona())){
            return "Ese Tipo de Persona no puede realizar este tipo de Colaboración!";
        }
        return null;
    }

    @Override
    public void incrementarPuntos(Colaborador colaborador){
        colaborador.incrementarPuntaje(this.monto * coeficiente);
    }

}