package modelo.colaboracion;

import lombok.NoArgsConstructor;
import modelo.personas.Colaborador;
import modelo.personas.TipoPersona;

import javax.persistence.*;
import java.util.Arrays;

@NoArgsConstructor
@Entity
@DiscriminatorValue("RECOMPENSA")
public class OfrecerRecompensa extends Colaboracion{

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "oferta_id", referencedColumnName = "id")
    private Oferta oferta;

    public OfrecerRecompensa(Oferta oferta) {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PJ);
        this.oferta = oferta;
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