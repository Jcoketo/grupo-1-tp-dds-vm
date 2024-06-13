package colaboracion;

import lombok.Setter;
import elementos.Heladera;
import personas.TipoPersona;
import personas.Colaborador;

import java.time.LocalDate;
import java.util.Arrays;

public class DonarVianda extends Colaboracion{
    private Vianda vianda;
    private Heladera heladera;
    @Setter private static Double coeficiente = 1.5;

    //CONSTRUCTOR PRINCIPAL
    public DonarVianda(Vianda vianda, Heladera heladera) {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PH);
        this.vianda = vianda;
        this.heladera = heladera;
    };

    // CONSTRUCTOR PARA IMPORTADOR CSV
    public DonarVianda(LocalDate fechaDonacion) {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PH);
        this.fechaColaboracion = fechaDonacion;
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
    public boolean validar(Colaborador colaborador) {
        return this.tiposPersonasHabilitadas.contains(colaborador.getTipo());
    }

    @Override
    public void incrementarPuntos(Colaborador colaborador) {
        colaborador.incrementarPuntaje(coeficiente);
    }

}