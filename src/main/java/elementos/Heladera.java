package elementos;

import colaboracion.Vianda;
import lombok.Getter;
import lombok.Setter;
import personas.Colaborador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Heladera {
    private PuntoEstrategico puntoEstrategico;
    @Setter private String nombre;
    @Setter private int viandasMaximas;
    private List<Vianda> viandas;
    @Getter private LocalDate fechaFuncionamiento;
    @Getter @Setter private Boolean activa;
    @Getter private Float temperaturaMaxima;
    @Getter private Float temperaturaMinima;
    List<Colaborador> colaboradoresSucriptos;
    Integer contadorFallas;
    Integer contadorViandasRetiradas;
    Integer contadorViandasColocadas;


    public Heladera(int capacidadMaxima, LocalDate fechaFuncionamiento){
        this.viandas = new ArrayList<Vianda>();
        this.viandasMaximas = capacidadMaxima;
        this.fechaFuncionamiento = fechaFuncionamiento;
        this.activa = true;
    }

    public void agregarVianda(Vianda... viandas) {
        Collections.addAll(this.viandas, viandas);
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

    public void marcarComoInactiva(){
        this.activa = false;
    }
}