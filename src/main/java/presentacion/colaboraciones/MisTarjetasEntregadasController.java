package presentacion.colaboraciones;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.Getter;
import lombok.Setter;
import modelo.colaboracion.Colaboracion;
import modelo.personas.PersonaVulnerable;
import modelo.personas.TipoDocumento;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioColaboradores;
import persistencia.RepositorioPersonasVulnerables;
import utils.GeneradorModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MisTarjetasEntregadasController implements Handler {
    private RepositorioPersonasVulnerables repoPersonasVulnerables = RepositorioPersonasVulnerables.getInstancia();

    public MisTarjetasEntregadasController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        Integer idPersona = context.sessionAttribute("idPersona");

        List<PersonaVulnerable> personasVulnerables = repoPersonasVulnerables.obtenerPersonasVulnerablesRegistradasPor(idPersona);

        if(personasVulnerables == null){
            personasVulnerables = List.of();}

        List<DatosPersonaVulnerable> personasRegistradas = this.getDatosPersonasVulnerables(personasVulnerables);

        model.put("tarjetas", personasRegistradas);

        model.put("nombreUsuario", context.sessionAttribute("nombreUsuario"));
        context.render("templates/tarjetasEntregadas.mustache", model);
    }

    private List<DatosPersonaVulnerable> getDatosPersonasVulnerables(List<PersonaVulnerable> personasVulnerables){
        return personasVulnerables.stream().map(persona ->
            new DatosPersonaVulnerable(persona.getNombreApellido(), persona.getFechaRegistro(), persona.getDocumento().getTipoDoc(), persona.getDocumento().getNumeroDoc(), persona.getMenoresACargo(), persona.getDireccion())).toList();
    }
}

@Getter
@Setter
class DatosPersonaVulnerable {
    private String nombre;
    private LocalDate fechaRegistro;
    private TipoDocumento tipoDocumento;
    private String numeroDocumento;
    private Integer menoresACargo;
    private String domicilio;
    private Boolean poseeDomicilio;

    public DatosPersonaVulnerable(String nombre, LocalDate fechaRegistro, TipoDocumento tipoDocumento, String numeroDocumento, Integer menoresACargo, String domicilio) {
        this.nombre = nombre;
        this.fechaRegistro = fechaRegistro;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.menoresACargo = menoresACargo;
        this.domicilio = domicilio;
        if(domicilio != null && !domicilio.equals("")){
            this.poseeDomicilio = Boolean.TRUE;
        } else {
            this.poseeDomicilio = Boolean.FALSE;
        }
    }
}