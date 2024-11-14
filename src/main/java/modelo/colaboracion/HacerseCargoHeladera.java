package modelo.colaboracion;

import lombok.Setter;
import modelo.elementos.Heladera;
import modelo.personas.Colaborador;
import modelo.personas.TipoPersona;
import javax.persistence.*;

import java.util.Arrays;

@Entity
@DiscriminatorValue("CARGO_HELADERA")
public class HacerseCargoHeladera extends Colaboracion{
    @OneToOne
    @JoinColumn(name = "heladera_id", referencedColumnName = "id")
    private Heladera heladera;

    @Transient
    @Setter private static Double coeficiente = 5.0;

    public HacerseCargoHeladera(Heladera heladera) {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PJ);
        this.heladera = heladera;
    }

    public HacerseCargoHeladera() {

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
        //TODO: este método se ejecutaría de manera CRON-TASK cada 30 días o una vez por mes (en un día determinado).
        // Por lo tanto incrementa el puntaje del colaborador en 5, en cada una de las heladeras que la Persona se hizo cargo.
        colaborador.incrementarPuntaje(coeficiente);
    }

}