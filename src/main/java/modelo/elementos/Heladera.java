package modelo.elementos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import modelo.colaboracion.Vianda;
import modelo.personas.Colaborador;
import modelo.personas.Tecnico;
import modelo.personas.Visita;
import modelo.suscripcion.Suscripcion;
import modelo.suscripcion.TipoSuscripcion;
import persistencia.RepositorioIncidentes;
import persistencia.RepositoriosTecnicos;
@Entity
@Table(name = "heladera")
public class Heladera {

    @Id
    @GeneratedValue
    @Getter private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "punto_estrategico_id", referencedColumnName = "id")
    @Getter @Setter private PuntoEstrategico puntoEstrategico;

    @Column
    @Setter @Getter private String nombre;

    @Column
    @Setter private int viandasMaximas;

    @OneToMany(mappedBy = "disponibleEn")
    @Getter private List<Vianda> viandas;

    @Column
    @Getter private LocalDate fechaFuncionamiento;

    @Column
    @Getter @Setter private Boolean activa;

    @Column
    @Getter private Float temperaturaMaxima;

    @Column
    @Getter private Float temperaturaMinima;

    @OneToMany(mappedBy = "heladera")
    @Getter private List<Suscripcion> colaboradoresSucriptos = new ArrayList<>();

    @Column
    @Getter private Integer contadorFallasSemanal = 0;

    @Column
    @Getter private Integer contadorViandasRetiradas = 0;

    @Column
    @Getter private Integer contadorViandasColocadas = 0;

    @Column
    @Getter @Setter private Boolean habilitado;

    @OneToMany(mappedBy = "heladera")
    private List<Visita> visitas = new ArrayList<>();

    @Column
    private Integer tiempoActivo;

    public Heladera(int capacidadMaxima, LocalDate fechaFuncionamiento, PuntoEstrategico puntoEstrategico) {
        this.viandas = new ArrayList<Vianda>();
        this.viandasMaximas = capacidadMaxima;
        this.fechaFuncionamiento = fechaFuncionamiento;
        this.activa = true;
        this.puntoEstrategico = puntoEstrategico;
    }

    public Heladera() {

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

    public void agregarSuscriptor(Suscripcion suscripcion){
        this.colaboradoresSucriptos.add(suscripcion);
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