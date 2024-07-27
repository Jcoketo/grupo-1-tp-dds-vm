package colaboracion;

import elementos.SeguimientoApertura;
import elementos.TipoSolicitud;
import lombok.Setter;
import elementos.Heladera;
import personas.TipoPersona;
import personas.Colaborador;
import repositorios.RepositorioSolicitudes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DistribucionDeViandas extends Colaboracion {
    private Heladera heladeraOrigen;
    private Heladera heladeraDestino;
    private List<Vianda>viandas;
    private MotivoDistribucion motivoDistribucion;
    @Setter private static Double coeficiente = 1.0;
    int viandasDistribuidas;


    // CONSTRUCTOR PRINCIPAL
    public DistribucionDeViandas(TipoPersona persona, Integer cantidadViandas, Heladera heladeraOrigen, Heladera heladeraDestino, MotivoDistribucion motivoDistribucion, LocalDate fechaDistribucion) {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PH);
        this.fechaColaboracion = fechaDistribucion;

        this.heladeraOrigen = heladeraOrigen;
        this.heladeraDestino = heladeraDestino;
        this.viandas = new ArrayList<>();
        this.motivoDistribucion = motivoDistribucion;
    }

    // CONSTRUCTOR PARA IMPORTADOR CSV
    public DistribucionDeViandas(LocalDate fechaDistribucion, Integer cantidadViandas)
    {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PH);
        this.fechaColaboracion  = fechaDistribucion;
        this.viandas = new ArrayList<>();

        this.heladeraDestino = null;
        this.heladeraOrigen = null;
        this.motivoDistribucion = null;
        this.viandasDistribuidas = 0;

    }

    @Override
    public void hacerColaboracion(Colaborador colaborador) {
        String text = validar(colaborador);
        if(text == null){
            colaborador.agregarColaboracion(this);
            RepositorioSolicitudes repo = RepositorioSolicitudes.getInstancia();
            repo.agregarSolicitud(new SeguimientoApertura(this.heladeraOrigen, colaborador, TipoSolicitud.APERTURA));

            switch (motivoDistribucion) {
                case DESPERFECTO:

                    for (Vianda vianda : viandas) {
                        if(!heladeraDestino.hayLugar()){
                            System.out.println("No hay lugar en la heladera destino");
                            break; // salgo del for
                        }
                        heladeraOrigen.retirarVianda(heladeraOrigen.getViandas().indexOf(vianda));
                        heladeraDestino.agregarVianda(vianda);
                        viandasDistribuidas += 1;
                    }
                    break;
                case FALTA_VIANDAS:
                    if(!heladeraDestino.hayLugar()){
                        System.out.println("No hay lugar en la heladera destino");
                        break; // salgo del for
                    }
                    for (Vianda vianda : viandas) {
                        heladeraOrigen.retirarVianda(heladeraOrigen.getViandas().indexOf(vianda));
                        heladeraDestino.agregarVianda(vianda);
                        viandasDistribuidas += 1;
                    }
                    break;
                default:

                    break;
            }

            incrementarPuntos(colaborador);
        }
        else {
            System.out.println("Error!!!");
            System.out.println(text);
        }
    }

    @Override
    public String validar(Colaborador colaborador) {
        if(heladeraOrigen.getHabilitado() && heladeraOrigen.getActiva() && this.tiposPersonasHabilitadas.contains(colaborador.getTipoPersona())){
            return null;
        }
        else if(heladeraOrigen.getHabilitado()){
            return "La heladera no está habilitada";
        }
        else if(heladeraOrigen.getActiva()){
            return "La heladera no está activa";
        } else {
            return "Ese Tipo de Persona no puede realizar este tipo de Colaboración!";
        }
    }

    @Override
    public void incrementarPuntos(Colaborador colaborador) {
        colaborador.incrementarPuntaje(this.viandasDistribuidas * coeficiente);
    }

}