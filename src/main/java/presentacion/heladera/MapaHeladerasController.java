package presentacion.heladera;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.Getter;
import lombok.Setter;
import modelo.elementos.Heladera;
import modelo.elementos.PuntoEstrategico;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioHeladeras;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapaHeladerasController implements Handler {

    public MapaHeladerasController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        // Obtén las heladeras del repositorio

        try {
            RepositorioHeladeras repoHeladeras = RepositorioHeladeras.getInstancia();
            List<Heladera> heladeras = repoHeladeras.obtenerHeladeras().stream().filter(heladera -> !heladera.getBajaLogica()).toList();

            List<HeladeraOrigenDestino> HeladeraOrigenDestino = getHeladerasDistribucion(heladeras);

            context.json(HeladeraOrigenDestino);
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            context.status(500);
            context.result("Error al obtener las heladeras");
        }
        // Devuelve las heladeras en formato JSON

    }

    private List<HeladeraOrigenDestino> getHeladerasDistribucion(List<Heladera> heladeras){
        return heladeras.stream()
                .map(heladera -> {
                    HeladeraOrigenDestino heladeraAux = new HeladeraOrigenDestino();
                    heladeraAux.setNombre(heladera.getNombre());
                    heladeraAux.setActiva(heladera.getActiva());
                    heladeraAux.setDisponibilidad(cantidadViandas(heladera));
                    heladeraAux.setId(heladera.getId());
                    heladeraAux.setNecesitaTrasladoDe(heladera.getViandas().size());
                    heladeraAux.setPunto(heladera.getPuntoEstrategico());
                    heladeraAux.setFechaFuncionamiento(heladera.getFechaFuncionamiento());
                    return heladeraAux;
                }).toList();
    }

    private Integer cantidadViandas(Heladera heladera){
        return heladera.getViandasMaximas() - heladera.getCantidadViandas();
    }
}
@Getter
@Setter
class HeladeraOrigenDestino {
    private int id;
    private String nombre;
    private Integer disponibilidad;
    private Boolean activa;
    private Integer necesitaTrasladoDe;
    private PuntoEstrategico punto;
    private LocalDate fechaFuncionamiento;

}