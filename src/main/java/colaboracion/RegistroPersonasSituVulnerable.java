package colaboracion;

import elementos.TarjetaPlastica;
import personas.Colaborador;
import personas.PersonaVulnerable;
import personas.TipoPersona;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegistroPersonasSituVulnerable extends Colaboracion{
    private List<TarjetaPlastica> tarjetasDisponibles;
    private List<TarjetaPlastica> tarjetasRepartidas;

    public RegistroPersonasSituVulnerable() {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PH);
        this.tarjetasDisponibles = new ArrayList<TarjetaPlastica>();
        this.tarjetasRepartidas = new ArrayList<TarjetaPlastica>();
    }

    @Override
    public void hacerColaboracion(Colaborador colaborador) {
        //TODO
    }

    @Override
    public boolean validar(Colaborador colaborador){
        return true;
    }

    @Override
    public void incrementarPuntos(Colaborador colaborador){
        //TODO
    }

    public void darAltaPersonaVulnerable(){
        //TODO
    }

    public void entregarTarjeta(List<PersonaVulnerable> personas){
        //TODO
    }
}