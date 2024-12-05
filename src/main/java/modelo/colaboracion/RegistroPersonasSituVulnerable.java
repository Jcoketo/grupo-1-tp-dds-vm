package modelo.colaboracion;

import lombok.Setter;
import modelo.elementos.TarjetaPlastica;
import modelo.personas.Colaborador;
import modelo.personas.PersonaVulnerable;
import modelo.personas.TipoPersona;
import javax.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@DiscriminatorValue("REGISTRO")
public class RegistroPersonasSituVulnerable extends Colaboracion{

    @Column
    private Integer cantidadTarjetas;

    @OneToMany
    @JoinColumn(name= "colaboracion_id", referencedColumnName = "id")
    private List<TarjetaPlastica> tarjetasDisponibles;

//    @OneToMany
//    private List<TarjetaPlastica> tarjetasRepartidas;

    @Setter private static Double coeficiente = 2.0;


    // Interpreto que en el constructor recibimos la cantidad de tarjetas a repartir
    // y la instanciamos cuando la repartimos
    public RegistroPersonasSituVulnerable(Integer cantidadTarjetas) {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PH);
        this.tarjetasDisponibles = new ArrayList<TarjetaPlastica>();

        this.cantidadTarjetas = cantidadTarjetas;
    }
    // CONSTRUCTOR PARA IMPORTADOR SCV
    public RegistroPersonasSituVulnerable(LocalDate fechaDonacion, Integer cantidadTarjetas) {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PH);
        this.tarjetasDisponibles = new ArrayList<TarjetaPlastica>();

        this.cantidadTarjetas = cantidadTarjetas;
        this.fechaColaboracion = fechaDonacion;
    }

    public RegistroPersonasSituVulnerable(Integer cantidadTarjetas, List<TarjetaPlastica> tarjetasDisponibles, LocalDate fechaDonacion) {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PH);
        this.tarjetasDisponibles = new ArrayList<TarjetaPlastica>();
        this.tarjetasDisponibles = tarjetasDisponibles;
        this.cantidadTarjetas = cantidadTarjetas;

    }

    public RegistroPersonasSituVulnerable() {

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
        } else if (colaborador.getPersona().getDireccion() == null) {
            return "Esa Persona no tiene una dirección!";
        }
        return null;
    }

    @Override
    public void incrementarPuntos(Colaborador colaborador){
        colaborador.incrementarPuntaje((double) this.cantidadTarjetas * coeficiente);
    } // Nosotros entendemos que las tarjetas ya fueron repartidas.

    @Override
    public Double conocerPuntaje(){return this.cantidadTarjetas * coeficiente; }

    @Override
    public String getClassName() {
        return "Registrar Personas Vulnerables";
    }

   /* public void darAltaPersonaVulnerable(){
        RepositorioPersonasVulnerables.getInstancia().agregarPersonaVulnerable(persona);
    }*/

    public void entregarTarjeta(List<PersonaVulnerable> personas){
        //TODO
    }

}