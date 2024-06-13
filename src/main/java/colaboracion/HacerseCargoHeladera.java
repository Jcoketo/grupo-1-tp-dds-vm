package colaboracion;

import lombok.Setter;
import elementos.Heladera;
import personas.Colaborador;
import personas.TipoPersona;

import java.util.Arrays;

public class HacerseCargoHeladera extends Colaboracion{
    private Heladera heladera;
    @Setter private static Double coeficiente = 5.0;

    public HacerseCargoHeladera(Heladera heladera) {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PJ);
        this.heladera = heladera;
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
        //TODO: este método se ejecutaría de manera CRON-TASK cada 30 días o una vez por mes (en un día determinado).
        // Por lo tanto incrementa el puntaje del colaborador en 5, en cada una de las heladeras que la Persona se hizo cargo.
        colaborador.incrementarPuntaje(coeficiente);
    }

}