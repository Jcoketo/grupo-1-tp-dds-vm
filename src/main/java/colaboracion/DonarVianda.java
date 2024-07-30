package colaboracion;

import elementos.SeguimientoApertura;
import elementos.TipoSolicitud;
import lombok.Setter;
import elementos.Heladera;
import personas.TipoPersona;
import personas.Colaborador;
import repositorios.RepositorioSolicitudes;

import java.time.LocalDate;
import java.util.Arrays;

public class DonarVianda extends Colaboracion{
    private Vianda vianda;
    private Heladera heladera;
    @Setter private static Double coeficiente = 1.5;

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

    //public void hacerVianda(parametros de vinda)+

    public void efectuarApertura(Colaborador colaborador){
        RepositorioSolicitudes repo = RepositorioSolicitudes.getInstancia();
        repo.agregarSolicitud(new SeguimientoApertura(this.heladera, colaborador, TipoSolicitud.APERTURA));
        // REGISTRAR EL USO EN LA TARJETA
    }

    public void solicitarAperturaHeladera(Colaborador colaborador, Heladera heladera){
        heladera.permitirIngreso();
        SeguimientoApertura seguimientoApertura = new SeguimientoApertura(heladera, colaborador, TipoSolicitud.SOLICITUD_APERTURA);
        RepositorioSolicitudes repositorioSolicitudes = RepositorioSolicitudes.getInstancia();
        repositorioSolicitudes.agregarSolicitud(seguimientoApertura);
    }

    @Override
    public void hacerColaboracion(Colaborador colaborador) {
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