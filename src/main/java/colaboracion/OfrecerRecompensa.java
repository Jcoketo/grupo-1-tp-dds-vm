package colaboracion;

import personas.Colaborador;
import personas.TipoPersona;

import java.util.Arrays;

public class OfrecerRecompensa extends Colaboracion{
    private Oferta oferta;

    public OfrecerRecompensa() {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PJ);
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
        // No especifica cuantos puntos gana el colaborador...
    }

}