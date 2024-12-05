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

    private RepositorioHeladeras repositorioHeladeras;

    public MapaHeladerasController(RepositorioHeladeras repo) {
        super();
        this.repositorioHeladeras = repo;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        // Obtén las heladeras del repositorio

        RepositorioHeladeras repoHeladeras = RepositorioHeladeras.getInstancia();
        List<Heladera> heladeras = repoHeladeras.obtenerHeladeras();

        List<HeladeraOrigenDestino> HeladeraOrigenDestino = getHeladerasDistribucion(heladeras);

        context.json(HeladeraOrigenDestino);
        // Devuelve las heladeras en formato JSON

        Map<String, Object> model = context.sessionAttribute("model");
        if (model == null) {
            model = new HashMap<>();
            context.sessionAttribute("model", model);
        }
        model.put("nombreUsuario", context.sessionAttribute("nombreUsuario"));
        context.render("templates/mapaHeladeras.mustache", model);

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
        return heladera.getViandasMaximas() - heladera.getViandas().size();
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