package presentacion.heladera;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.Getter;
import lombok.Setter;
import modelo.elementos.Heladera;
import modelo.elementos.PuntoEstrategico;
import modelo.personas.PersonaJuridica;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioColaboradores;
import utils.GeneradorModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class MapaMisHeladerasController implements Handler {

    private static RepositorioColaboradores repoColaboradores = RepositorioColaboradores.getInstancia();

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);
        try {
            Integer idPersona = context.sessionAttribute("idPersona");
            PersonaJuridica persona = repoColaboradores.traerPersonaPorIdJuridica(idPersona);
            List<Heladera> heladeras = persona.getHeladeras();

            List<HeladerasJuridica> misHeladeras = getMisHeladeras(heladeras);

            context.json(misHeladeras);

        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            context.status(500);
            context.result("Error al obtener las heladeras");
        }
    }

    private List<HeladerasJuridica> getMisHeladeras(List<Heladera> heladeras){
        return heladeras.stream()
                .map(heladera -> {
                    HeladerasJuridica heladeraAux = new HeladerasJuridica();
                    heladeraAux.setNombre(heladera.getNombre());
                    heladeraAux.setActiva(heladera.getActiva());
                    heladeraAux.setDisponibilidad(cantidadViandas(heladera));
                    heladeraAux.setId(heladera.getId());
                    heladeraAux.setPunto(heladera.getPuntoEstrategico());
                    heladeraAux.setFechaFuncionamiento(heladera.getFechaFuncionamiento());
                    heladeraAux.setTieneBaja(heladera.getBajaLogica());
                    return heladeraAux;
                }).toList();
    }

    private Integer cantidadViandas(Heladera heladera){
        return heladera.getViandasMaximas() - heladera.getCantidadViandas();
    }
}
@Getter
@Setter
class HeladerasJuridica {
    private int id;
    private String nombre;
    private Integer disponibilidad;
    private Boolean activa;
    private PuntoEstrategico punto;
    private LocalDate fechaFuncionamiento;
    private Boolean tieneBaja;

}