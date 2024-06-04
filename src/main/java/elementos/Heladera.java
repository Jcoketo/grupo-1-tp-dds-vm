package elementos;

import colaboracion.Vianda;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Heladera {
    private Ubicacion ubicacion;
    private String nombre;
    private int viandasMaximas;
    private List<Vianda> viandas;
    @Getter private LocalDate fechaFuncionamiento;
    private Boolean activa;
    @Getter private Float temperaturaMaxima;
    @Getter private Float temperaturaMinima;

    public void agregarVianda(Vianda... viandas) {
        this.viandas.addAll(Arrays.asList(viandas));
    }

    public void setTempMaxima(Float temperatura){
        this.temperaturaMaxima = temperatura;
    }

    public void setTempMinima(Float temperatura){
        this.temperaturaMinima = temperatura;
    }
}