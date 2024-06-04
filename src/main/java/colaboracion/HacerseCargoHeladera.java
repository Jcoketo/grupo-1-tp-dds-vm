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
        //TODO
        return true;
    }

    @Override
    public void incrementarPuntos(Colaborador colaborador){
        //TODO
    }
}