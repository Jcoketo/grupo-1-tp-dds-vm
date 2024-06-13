package colaboracion;

import elementos.TarjetaPlastica;
import personas.Colaborador;
import personas.PersonaVulnerable;
import personas.TipoPersona;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegistroPersonasSituVulnerable extends Colaboracion{
    private Integer cantidadTarjetas;
    private List<TarjetaPlastica> tarjetasDisponibles;
    private List<TarjetaPlastica> tarjetasRepartidas;
    private static Double coeficiente = 2.0;


    // Interpreto que en el constructor recibimos la cantidad de tarjetas a repartir
    // y la instanciamos cuando la repartimos
    public RegistroPersonasSituVulnerable(Integer cantidadTarjetas) {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PH);
        this.tarjetasDisponibles = new ArrayList<TarjetaPlastica>();
        this.tarjetasRepartidas = new ArrayList<TarjetaPlastica>();

        this.cantidadTarjetas = cantidadTarjetas;
    }
    // CONSTRUCTOR PARA IMPORTADOR SCV
    public RegistroPersonasSituVulnerable(LocalDate fechaDonacion, Integer cantidadTarjetas) {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PH);
        this.tarjetasDisponibles = new ArrayList<TarjetaPlastica>();
        this.tarjetasRepartidas = new ArrayList<TarjetaPlastica>();

        this.cantidadTarjetas = cantidadTarjetas;
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
            if(colaborador.getDireccion() != null) {
                System.out.println("Esa Persona no tiene una dirección!");
            }
            else {
                System.out.println("Ese Tipo de Persona no puede realizar este tipo de Colaboración!");
            }
        }
    }

    @Override
    public boolean validar(Colaborador colaborador){
        return this.tiposPersonasHabilitadas.contains(colaborador.getTipo()) && colaborador.getDireccion() != null;
    }

    @Override
    public void incrementarPuntos(Colaborador colaborador){
        colaborador.incrementarPuntaje((double) this.cantidadTarjetas * coeficiente);
    } // Nosotros entendemos que las tarjetas ya fueron repartidas.

    public void darAltaPersonaVulnerable(){
        //TODO
    }

    public void entregarTarjeta(List<PersonaVulnerable> personas){
        //TODO
    }

}