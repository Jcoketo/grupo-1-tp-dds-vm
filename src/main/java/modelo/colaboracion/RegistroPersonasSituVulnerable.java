package modelo.colaboracion;

import lombok.Getter;
import lombok.Setter;
import modelo.elementos.TarjetaPlastica;
import modelo.personas.Colaborador;
import modelo.personas.PersonaVulnerable;
import modelo.personas.TipoPersona;
import javax.persistence.*;
import javax.ws.rs.DefaultValue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@DiscriminatorValue("REGISTRO")
public class RegistroPersonasSituVulnerable extends Colaboracion{

    @Column
    @Setter
    private Integer cantidadTarjetas;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name= "colaboracion_id", referencedColumnName = "id")
    @Getter
    private List<TarjetaPlastica> tarjetas;

    @Column
    @DefaultValue("0")
    @Getter @Setter
    private Integer cantidadRepartida;

    @Setter private static Double coeficiente = 2.0;


    // Interpreto que en el constructor recibimos la cantidad de tarjetas a repartir
    // y la instanciamos cuando la repartimos
    public RegistroPersonasSituVulnerable(Integer cantidadTarjetas) {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PH);
        this.tarjetas = new ArrayList<TarjetaPlastica>();

        this.cantidadTarjetas = cantidadTarjetas;
        this.cantidadRepartida = 0;

    }
    // CONSTRUCTOR PARA IMPORTADOR CSV
    public RegistroPersonasSituVulnerable(LocalDate fechaDonacion, Integer cantidadTarjetas) {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PH);
        this.tarjetas = new ArrayList<TarjetaPlastica>();

        this.cantidadTarjetas = cantidadTarjetas;
        this.fechaColaboracion = fechaDonacion;
        this.cantidadRepartida = 0;
        this.porCSV = Boolean.TRUE;
    }

    public RegistroPersonasSituVulnerable(Integer cantidadTarjetas, List<TarjetaPlastica> tarjetas, LocalDate fechaDonacion) {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PH);
        this.tarjetas = tarjetas;
        this.cantidadTarjetas = cantidadTarjetas;
        this.cantidadRepartida = 0;
        this.fechaColaboracion = fechaDonacion;

    }

    public RegistroPersonasSituVulnerable() {

    }


    @Override
    public void hacerColaboracion(Colaborador colaborador) {
        //String text = validar(colaborador);
        //if(text == null){
            incrementarPuntos(colaborador);
            //colaborador.agregarColaboracion(this);
       // }
        //else {
           // System.out.println("Error!!!");
            //System.out.println(text);
        //}
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
    public void incrementarPuntos(Colaborador colaborador){ // Se llama cada vez que se reparte una tarjeta --> es solo coeficiente
        colaborador.incrementarPuntaje(coeficiente);
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

}