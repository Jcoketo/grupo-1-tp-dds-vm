package colaboracion;

import elementos.TarjetaPlastica;
import personas.Colaborador;
import personas.PersonaVulnerable;
import personas.TipoPersona;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegistroPersonasSituVulnerable extends Colaboracion{
    private Integer cantidadTarjetas;
    private List<TarjetaPlastica> tarjetasDisponibles;
    private List<TarjetaPlastica> tarjetasRepartidas;


    // interpreto que en el constructor recibimos la cantidad de tarjetas a repartir
    // y la instanciamos cuando la repartimos
    public RegistroPersonasSituVulnerable(Integer cantidadTarjetas) {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PH);
        this.tarjetasDisponibles = new ArrayList<TarjetaPlastica>();
        this.tarjetasRepartidas = new ArrayList<TarjetaPlastica>();

        this.cantidadTarjetas = cantidadTarjetas;
    }
    // CONSTRUCTOR PARA IMPORTADOR SCV
    public RegistroPersonasSituVulnerable(Integer cantidadTarjetas, LocalDateTime fechaDonacion) {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PH);
        this.tarjetasDisponibles = new ArrayList<TarjetaPlastica>();
        this.tarjetasRepartidas = new ArrayList<TarjetaPlastica>();

        this.cantidadTarjetas = cantidadTarjetas;
        this.fechaDonacion = fechaDonacion;
    }

    @Override
    public void hacerColaboracion(Colaborador colaborador) {
        //TODO
    }

    @Override
    public boolean validar(Colaborador colaborador){
        return this.tiposPersonasHabilitadas.contains(colaborador.getTipo());
    }

    @Override
    public void incrementarPuntos(Colaborador colaborador){
        colaborador.incrementarPuntaje((float) this.cantidadTarjetas);
    } //entiendo que ya fueron repartidas

    public void darAltaPersonaVulnerable(){
        //TODO
    }

    public void entregarTarjeta(List<PersonaVulnerable> personas){
        //TODO
    }

}