package colaboracion;

import elementos.SolicitudApertura;
import lombok.Setter;
import elementos.Heladera;
import personas.TipoPersona;
import personas.Colaborador;
import repositorios.RepositorioSolicitudes;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;

public class DonarVianda extends Colaboracion{
    private Vianda vianda;
    private Heladera heladera;
    @Setter private static Double coeficiente = 1.5;
    private SolicitudApertura solicitud;


    //CONSTRUCTOR PRINCIPAL
    public DonarVianda(Vianda vianda, Heladera heladera) {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PH);
        this.vianda = vianda;
        this.heladera = heladera;
    };

    // CONSTRUCTOR PARA IMPORTADOR CSV
    public DonarVianda(LocalDate fechaDonacion) {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PH);
        this.fechaColaboracion = fechaDonacion;
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

    @Override
    public void hacerColaboracion(Colaborador colaborador) {
        if ( Duration.between(this.solicitud.getFechaSolicitud(), this.solicitud.getAperturaFehaciente()).toHours() > this.solicitud.getHorasDeApertura()){
            System.out.println("El usuario carece de permisos para realizar dicha acción.");
            return;
        }
        this.efectuarApertura(colaborador);
        colaborador.aumentarCantidadDonacion();
        String text = validar(colaborador);
        if(text == null){
            incrementarPuntos(colaborador);
            colaborador.agregarColaboracion(this);

            heladera.agregarVianda(this.vianda);
        }
        else {
            System.out.println("Error!!!");
            System.out.println(text);
        }

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

}