package elementos;

import lombok.Getter;
import ubicacion.Ubicacion;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Heladera {
    private Ubicacion ubicacion;
    private List<Vianda> viandas;
    @Getter
    private LocalDateTime fechaFuncionamiento;
    private String nombre;
    private Integer viandasMaximas;
    private Boolean activa;

    public void agregarVianda(Vianda... viandas) {
        this.viandas.addAll(Arrays.asList(viandas));
    }
}

