package colaboraciones;

import elementos.Heladera;
import elementos.Vianda;
import enums.TipoPersona;

import java.time.LocalDateTime;

public class DonarVianda extends Colaboracion{
    private Vianda vianda;
    private Heladera heladera;

    static {
        personasHabilitadas.add(TipoPersona.PH);
    }

    public DonarVianda(Vianda vianda, Heladera heladera, TipoPersona persona) {
        this.vianda = vianda;
        this.heladera = heladera;

        this.persona = persona;
        //TODO
    }

    public DonarVianda(TipoPersona persona, LocalDateTime fechaDonacion) {
        this.persona = persona;
        this.fechaDonacion = fechaDonacion;
        //TODO
    }

    @Override
    public void hacerColaboracion() {
        try {
            this.validarPersona(persona);
        } catch (Exception e) {
            e.printStackTrace();
        }

        heladera.agregarVianda(vianda);

        //TODO
    }

    @Override
    public String getTipo() {
        return "DONAR_VIANDA";
    }

}
