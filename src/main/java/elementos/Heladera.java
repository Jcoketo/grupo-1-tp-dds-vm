package elementos;

import colaboracion.Vianda;
import lombok.Getter;
import lombok.Setter;

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
}