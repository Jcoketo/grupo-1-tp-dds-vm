package colaboracion;

import elementos.Heladera;
import personas.TipoPersona;
import personas.Colaborador;

import java.time.LocalDateTime;
import java.util.Arrays;

public class DonarVianda extends Colaboracion{
    private Vianda vianda;
    private Heladera heladera;

    //CONSTRUCTOR PRINCIPAL
    public DonarVianda(Vianda vianda, Heladera heladera) {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PH);
        this.vianda = vianda;
        this.heladera = heladera;
    };

    // CONSTRUCTOR PARA IMPORTADOR CSV
    public DonarVianda(LocalDateTime fechaDonacion) {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PH);
        this.fechaColaboracion = fechaDonacion;
    }

    @Override
    public void hacerColaboracion(Colaborador colaborador) {
        //TODO
        incrementarPuntos(colaborador);
    }

    @Override
    public boolean validar(Colaborador colaborador){
        return this.tiposPersonasHabilitadas.contains(colaborador.getTipo());
    }

    @Override
    public void incrementarPuntos(Colaborador colaborador){ colaborador.incrementarPuntaje(1.5F);}

    // se va a incrementar en 1,5 puntos por cada donacion de vianda
    /*
    @Override
    public String getTipo() {
        return "DONAR_VIANDA";
    }*/
}