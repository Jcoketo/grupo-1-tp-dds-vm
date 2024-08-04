package elementos;

import colaboracion.Vianda;
import lombok.Getter;
import lombok.Setter;
import personas.Colaborador;
import personas.Tecnico;
import repositorios.RepositorioIncidentes;
import repositorios.RepositoriosTecnicos;
import suscripcion.ColaboradorSuscripto;
import suscripcion.TipoSuscripcion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Heladera {
    @Getter @Setter private PuntoEstrategico puntoEstrategico;
    @Setter private String nombre;
    @Setter private int viandasMaximas;
    @Getter private List<Vianda> viandas;
    @Getter private LocalDate fechaFuncionamiento;
    @Getter @Setter private Boolean activa;
    @Getter private Float temperaturaMaxima;
    @Getter private Float temperaturaMinima;
    @Getter private List<ColaboradorSuscripto> colaboradoresSucriptos = new ArrayList<>();
    @Getter private Integer contadorFallasSemanal = 0;
    @Getter private Integer contadorViandasRetiradas = 0;
    @Getter private Integer contadorViandasColocadas = 0;
    @Getter @Setter private Boolean habilitado;
    private List<Visita> visitas = new ArrayList<>();

    private Integer tiempoActivo;

    public Heladera(int capacidadMaxima, LocalDate fechaFuncionamiento, PuntoEstrategico puntoEstrategico) {
        this.viandas = new ArrayList<Vianda>();
        this.viandasMaximas = capacidadMaxima;
        this.fechaFuncionamiento = fechaFuncionamiento;
        this.activa = true;
        this.puntoEstrategico = puntoEstrategico;
    }

    public void agregarVianda(Vianda vianda) {
        if (this.viandas.size() < this.viandasMaximas) { // si es menor significa que por lo menos hay n - 1 viandas
            this.viandas.add(vianda);
            this.contadorViandasColocadas++;
            if ( ( this.viandasMaximas - this.viandas.size() ) <= 5 && this.colaboradoresSucriptos != null) // no damos posibilidad a que el numero sea mayor ya que sino es muy poco performante
                                                                     //  si quedan 30 lugares libres va a hacer siempre este bloque de codigo
            { this.colaboradoresSucriptos.stream().filter(colab -> colab.getTipoSuscripcion() == TipoSuscripcion.POCO_ESPACIO
                                && colab.getLimiteViandasMaximas() == (this.viandasMaximas - this.viandas.size()))
                                .forEach(colab -> colab.notificarmeAlerta());
            }
        } else {
            // TODO no deberia ser una excepcion, deberia ser un mensaje de error
            throw new IndexOutOfBoundsException("No se pueden agregar más viandas");
        }
    }

    public Vianda retirarVianda(Integer indice){
        if (indice >= 0 && indice < this.viandas.size()) {
            this.contadorViandasRetiradas++;
            if ( ( this.viandas.size() ) <= 5  && this.colaboradoresSucriptos != null) {
                this.colaboradoresSucriptos.stream().filter(colab -> colab.getTipoSuscripcion() == TipoSuscripcion.QUEDAN_POCAS
                                && colab.getLimiteViandasMinimas() == this.viandas.size())
                                .forEach(colab -> colab.notificarmeAlerta());
            }
            return this.viandas.remove((int)indice);
        } else {
            // TODO no deberia ser una excepcion, deberia ser un mensaje de error
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
    }

    public void resetearContador(String tipo){
        switch (tipo){
            case "colocadas":
                this.contadorViandasColocadas = 0;
                break;
            case "retiradas":
                this.contadorViandasRetiradas = 0;
                break;
            case "fallas":
                this.contadorFallasSemanal = 0;
                break;
        }

    }

    public void setTempMaxima(Float temperatura){
        this.temperaturaMaxima = temperatura;
    }

    public void setTempMinima(Float temperatura){
        this.temperaturaMinima = temperatura;
    }

    public void reportarFalla(Colaborador colab, String motivo, String foto){
        this.marcarComoInactiva();
        this.contadorFallasSemanal++;

        FallaTecnica falla = new FallaTecnica(this, colab, motivo, foto);

        RepositorioIncidentes repo = RepositorioIncidentes.getInstancia();
        repo.agregar(falla);

        RepositoriosTecnicos tecnicos = RepositoriosTecnicos.getInstancia();
        if(!tecnicos.hayTecnicos()){
            System.out.println("No hay técnicos disponibles");
        }else {
            Tecnico tecnico = tecnicos.obtenerTecnicoCercano(this.getPuntoEstrategico().getAreas());
            tecnico.notificarFalla(this, falla);
        }
    }

    void marcarComoInactiva(){
        this.activa = false;
        if(this.colaboradoresSucriptos != null)
            this.colaboradoresSucriptos.stream().filter(colab -> colab.getTipoSuscripcion() == TipoSuscripcion.DESPERFECTO).forEach(colab -> colab.notificarmeAlerta());
        //this.detenerTareaPeriodica(); // CANCELAR EL PERMITIR INGRESO
    }


    /*public void detenerTareaPeriodica() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
            System.out.println("Scheduler detenido");
        }
    }*/

    public void agregarSuscriptor(ColaboradorSuscripto colaboradorSuscripto){
        this.colaboradoresSucriptos.add(colaboradorSuscripto);
    }

    public Boolean permitirIngreso() {
        if (this.activa) {
            /*
            this.habilitado = true;
            scheduler.schedule(() -> {

                this.habilitado = false;
                scheduler.shutdown();

            }, tiempoActivo, TimeUnit.HOURS);

             */

            return Boolean.TRUE;
        }
        else {
            System.out.println("La heladera no está activa");
            return Boolean.FALSE;
            }
    }


    /*public void setearTiempoActivo(Integer tiempoActivo){
        this.tiempoActivo = tiempoActivo;
    }*/
    //TODO este tiempo ahora esta en la solicitud

    public Boolean hayLugar(){
        return this.viandas.size() < this.viandasMaximas;
    }

    public void marcarComoActiva() {
        this.activa = true;
    }

    public void agregarVisita(Visita visita) {
        this.visitas.add(visita);
    }
}