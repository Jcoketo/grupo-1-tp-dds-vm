package modelo.colaboracion;

import modelo.elementos.SolicitudApertura;
import lombok.Setter;
import modelo.elementos.Heladera;
import modelo.personas.Colaborador;
import modelo.personas.TipoPersona;
import persistencia.RepositorioSolicitudes;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;

@Entity
@DiscriminatorValue("DONACION_VIANDA")
public class DonarVianda extends Colaboracion{

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "vianda_id", referencedColumnName = "id")
    private Vianda vianda;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "heladera_id", referencedColumnName = "id")
    private Heladera heladera;

    @Transient
    @Setter private static Double coeficiente = 1.5;

    @OneToOne
    @JoinColumn(name = "solicitud_apertura_id", referencedColumnName = "id")
    private SolicitudApertura solicitud;

    //CONSTRUCTOR PRINCIPAL
    public DonarVianda(Vianda vianda, Heladera heladera, LocalDate fechaDonacion) {
        this.fechaColaboracion = fechaDonacion;
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PH);
        this.vianda = vianda;
        this.heladera = heladera;
    };


    // CONSTRUCTOR PARA IMPORTADOR CSV
    public DonarVianda(LocalDate fechaDonacion) {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PH);
        this.fechaColaboracion = fechaDonacion;
    }

    public DonarVianda() {

    }

    @Override
    public void hacerColaboracion(Colaborador colaborador) {
        /*if ( Duration.between(this.solicitud.getFechaSolicitud(), this.solicitud.getAperturaFehaciente()).toHours() > this.solicitud.getHorasDeApertura()){
            System.out.println("El usuario carece de permisos para realizar dicha acción.");
            return;
        }*/
        // TODO
        //this.efectuarApertura(colaborador);
        colaborador.aumentarCantidadDonacion();
        //String text = validar(colaborador);
        //if(text == null){
            incrementarPuntos(colaborador);
            colaborador.agregarColaboracion(this);

            heladera.agregarVianda(this.vianda);
        //}
        //else {
         //   System.out.println("Error!!!");
         //   System.out.println(text);
        //}

    }

    @Override
    public String validar(Colaborador colaborador) {
        if(heladera.getHabilitado() && heladera.getActiva() && this.tiposPersonasHabilitadas.contains(colaborador.getTipoPersona())){
            return null;
        }
        else if(heladera.getHabilitado()){
            return "La heladera no está habilitada";
        }
        else if(heladera.getActiva()){
            return "La heladera no está activa";
        } else {
            return "Ese Tipo de Persona no puede realizar este tipo de Colaboración!";
        }
    }

    @Override
    public void incrementarPuntos(Colaborador colaborador) {
        colaborador.incrementarPuntaje(coeficiente);
    }

    public void solicitarAperturaHeladera(Colaborador colaborador, Heladera heladera){
        if( !heladera.permitirIngreso() ) {
            System.out.println("La heladera ya se encuentra inhabilitada");
            return;
        }
        SolicitudApertura seguimientoApertura = new SolicitudApertura(heladera, colaborador);
        RepositorioSolicitudes repositorioSolicitudes = RepositorioSolicitudes.getInstancia();
        repositorioSolicitudes.agregarSolicitud(seguimientoApertura);
        this.solicitud = seguimientoApertura;

        if (colaborador.getTarjeta() == null) {
            colaborador.solicitarTarjeta();
        }

    }

    public void efectuarApertura(Colaborador colaborador){
        if(this.solicitud != null){
            RepositorioSolicitudes repositorioSolicitudes = RepositorioSolicitudes.getInstancia();
            repositorioSolicitudes.cambiarEstadoAFehaciente(this.solicitud);
        }
        else {
            System.out.println("No se ha solicitado la apertura de la heladera");
            return;
        }
    }

}