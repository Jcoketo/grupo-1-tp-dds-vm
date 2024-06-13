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
        if(validar(colaborador)){
            incrementarPuntos(colaborador);
            colaborador.agregarColaboracion(this);
        }
        else {
            System.out.println("Error!!!");
            System.out.println("Ese Tipo de Persona no puede realizar este tipo de Colaboración!");
        }
    }

    @Override
    public boolean validar(Colaborador colaborador){
        return this.tiposPersonasHabilitadas.contains(colaborador.getTipo());
    }

    @Override
    public void incrementarPuntos(Colaborador colaborador){
        colaborador.incrementarPuntaje(this.monto * coeficiente);
    }

}