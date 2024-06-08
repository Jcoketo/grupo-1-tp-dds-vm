package colaboracion;

import elementos.Heladera;
import personas.Colaborador;
import personas.TipoPersona;

import java.util.Arrays;

public class HacerseCargoHeladera extends Colaboracion{
    private Heladera heladera;

    public HacerseCargoHeladera() {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PJ);
    }

    @Override
    public void hacerColaboracion(Colaborador colaborador) {
        //TODO
    }

    @Override
    public boolean validar(Colaborador colaborador){
        return this.tiposPersonasHabilitadas.contains(colaborador.getTipo());
    }

    @Override
    public void incrementarPuntos(Colaborador colaborador){
        //TODO
        // ENTIENDO QUE LA CRONE TASK DEBERIA LLAMAR A ESTE METODO POR CADA HELADERA QUE SE HACE CARGO
        // ENTONCES VA A INCREMENTAR 5 PUNTOS POR CADA HELADERA QUE SE HACE CARGO
        colaborador.incrementarPuntaje(5F);
    }

}