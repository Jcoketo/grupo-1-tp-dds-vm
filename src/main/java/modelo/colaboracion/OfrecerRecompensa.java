package modelo.colaboracion;

import modelo.personas.Colaborador;
import modelo.personas.TipoPersona;

import java.util.Arrays;

public class OfrecerRecompensa extends Colaboracion{
    private Oferta oferta;

    public OfrecerRecompensa() {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PJ);
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
        // No especifica cuantos puntos gana el colaborador...
    }

}