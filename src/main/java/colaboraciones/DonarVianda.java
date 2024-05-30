package colaboraciones;

import elementos.Heladera;
import elementos.Vianda;
import enums.TipoPersona;
import personas.Colaborador;

import java.time.LocalDateTime;

public class DonarVianda extends Colaboracion{
    private Vianda vianda;
    private Heladera heladera;

    static {
        personasHabilitadas.add(TipoPersona.PH);
    }
    //CONSTRUCTOR
    public DonarVianda(Vianda vianda, Heladera heladera, TipoPersona persona) {
        this.vianda = vianda;
        this.heladera = heladera;

        //this.persona = persona;
        //TODO
    }

    public DonarVianda(TipoPersona persona, LocalDateTime fechaDonacion) {

        if (persona == TipoPersona.PH) {
            //this.fechaDonacion = fechaDonacion;
        }

        //TODO
    }

    @Override
    public void hacerColaboracion(Colaborador colaborador) {

        heladera.agregarVianda(vianda);

        //TODO
    }

    @Override
    public String getTipo() {
        return "DONAR_VIANDA";
    }

}
