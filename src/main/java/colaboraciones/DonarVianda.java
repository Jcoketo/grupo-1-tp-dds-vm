package colaboraciones;

import elementos.Heladera;
import elementos.Vianda;
import enums.TipoPersona;

public class DonarVianda extends Colaboracion{
    private Vianda vianda;
    private Heladera heladera;

    public DonarVianda(Vianda vianda, Heladera heladera, TipoPersona persona) {
        this.vianda = vianda;
        this.heladera = heladera;

        this.persona = persona;
        super.personasHabilitadas.add(TipoPersona.PH);
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
