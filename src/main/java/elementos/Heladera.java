package elementos;

import colaboracion.Vianda;
import lombok.Getter;
import lombok.Setter;
import personas.Colaborador;
import repositorios.RepositorioColaboradores;
import repositorios.RepositorioIncidentes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Heladera {
    private PuntoEstrategico puntoEstrategico;
    @Setter private String nombre;
    @Setter private int viandasMaximas;
    @Getter private List<Vianda> viandas;
    @Getter private LocalDate fechaFuncionamiento;
    @Getter @Setter private Boolean activa;
    @Getter private Float temperaturaMaxima;
    @Getter private Float temperaturaMinima;
    private List<Colaborador> colaboradoresSucriptos;
    private Integer contadorFallas;
    private Integer contadorViandasRetiradas;
    private Integer contadorViandasColocadas;
    @Getter private Boolean habilitado;

    private Integer tiempoActivo;

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public Heladera(int capacidadMaxima, LocalDate fechaFuncionamiento){
        this.viandas = new ArrayList<Vianda>();
        this.viandasMaximas = capacidadMaxima;
        this.fechaFuncionamiento = fechaFuncionamiento;
        this.activa = true;
    }

    public void agregarVianda(Vianda vianda) {
        if (this.viandas.size() < this.viandasMaximas) { // si es menor significa que por lo menos hay n - 1 viandas
            this.viandas.add(vianda);
        } else {
            // TODO no deberia ser una excepcion, deberia ser un mensaje de error
            throw new IndexOutOfBoundsException("No se pueden agregar más viandas");
        }
    }

    public Vianda retirarVianda(Integer indice){
        if (indice >= 0 && indice < this.viandas.size()) {
            return this.viandas.remove((int)indice);
        } else {
            // TODO no deberia ser una excepcion, deberia ser un mensaje de error
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
    }

    public void setTempMaxima(Float temperatura){
        this.temperaturaMaxima = temperatura;
    }

    public void setTempMinima(Float temperatura){
        this.temperaturaMinima = temperatura;
    }

    public void evaluarCasoParaNotificar(){
        //TODO
    }

    public void reportarIncidente(Incidente incidente){
        //TODO, falta registrar fecha y hora del mismo, en qué heladera ocurrió y el tipo
        this.marcarComoInactiva();

    }

    public void reportarFalla(Colaborador colab, String motivo, String foto){
        this.marcarComoInactiva();
        this.contadorFallas++;

        FallaTecnica falla = new FallaTecnica(this, colab, motivo, foto);

        RepositorioIncidentes repo = RepositorioIncidentes.getInstancia();
        repo.agregar(falla);

    }

    void marcarComoInactiva() {
        this.activa = false;
        this.detenerTareaPeriodica(); // CANCELAR EL PERMITIR INGRESO
    }


    public void detenerTareaPeriodica() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
            System.out.println("Scheduler detenido");
        }
    }

    public void permitirIngreso() {
        if (this.activa) {
            this.habilitado = true;
            scheduler.schedule(() -> {

                this.habilitado = false;
                scheduler.shutdown();

            }, tiempoActivo, TimeUnit.HOURS);
        }
        else {
            System.out.println("La heladera no está activa");
            }
    }


    public void setearTiempoActivo(Integer tiempoActivo){
        this.tiempoActivo = tiempoActivo;
    }

    public Boolean hayLugar(){
        return this.viandas.size() < this.viandasMaximas;
    }
}