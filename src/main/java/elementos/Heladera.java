package elementos;

import colaboracion.Vianda;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Heladera {
    private Ubicacion ubicacion;
    private List<Vianda> viandas;
    @Getter
    private LocalDateTime fechaFuncionamiento;
    private String nombre;
    private int viandasMaximas;
    private Boolean activa;
    private  Float temperaturaMaxima;
    private  Float temperaturaMinima;

    public void agregarVianda(Vianda... viandas) {
        this.viandas.addAll(Arrays.asList(viandas));
    }
}

