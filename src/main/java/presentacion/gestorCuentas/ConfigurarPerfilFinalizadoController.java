package presentacion.gestorCuentas;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.Getter;
import lombok.Setter;
import modelo.colaboracion.FrecuenciaDonacion;
import modelo.personas.*;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioColaboradores;
import utils.GeneradorModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ConfigurarPerfilFinalizadoController implements Handler {

    RepositorioColaboradores repoColaboradores = RepositorioColaboradores.getInstancia();

    public ConfigurarPerfilFinalizadoController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        NotificacionCambio notificacionCambio = new NotificacionCambio();

        TipoPersona tipoPer = context.sessionAttribute("tipoPersona");
        Integer idPersona = context.sessionAttribute("idPersona");

        // DATOS COMPARTIDOS
        String telefono = context.formParam("telefono");
        String direccion = context.formParam("direccion");
        String email = context.formParam("email");

        if (  !telefono.equals("")  &&  !telefono.matches("[0-9]{8,10}")  )  {
            notificacionCambio.error("El teléfono debe tener entre 8 y 10 dígitos");
            context.sessionAttribute("notificacionCambio", notificacionCambio);
            context.redirect("/configurarPerfil");
            return;
        }

        if ( !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$") )  {
            notificacionCambio.error("El mail no es válido");
            context.sessionAttribute("notificacionCambio", notificacionCambio);
            context.redirect("/configurarPerfil");
            return;
        }

        /*  **************************************************************** */
        //                 P E R S O N A   H U M A N A
        /*  **************************************************************** */
        if (tipoPer == TipoPersona.PH) {
            PersonaHumana personaModificada = repoColaboradores.traerPersonaPorIdFisica(idPersona);
            String nombre = context.formParam("nombre");
            String apellido = context.formParam("apellido");

            String fNac = context.formParam("fechaNacimiento");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fechaNacimiento = LocalDate.parse(fNac, formatter);

            if (nombre.equals("") || apellido.equals("") || fNac.equals("") )  {
                notificacionCambio.error("Los campos nombre, apellido y fecha de nacimiento son obligatorios");
                context.sessionAttribute("notificacionCambio", notificacionCambio);
                context.redirect("/configurarPerfil");
                return;
            }

            personaModificada.setNombre(nombre);
            personaModificada.setApellido(apellido);
            personaModificada.setDireccion(direccion);
            personaModificada.setTelefono(telefono);
            personaModificada.setEmail(email);
            personaModificada.setFechaNacimiento(fechaNacimiento);
            repoColaboradores.actualizarPersona(personaModificada);

        }

        /*  **************************************************************** */
        //                 P E R S O N A   J U R I D I C A
        /*  **************************************************************** */
        else if (tipoPer == TipoPersona.PJ) {
            PersonaJuridica personaModificada = repoColaboradores.traerPersonaPorIdJuridica(idPersona);
            String nombre = context.formParam("nombre");
            //String cuit = context.formParam("cuit");
            //String cuitViejo = context.formParam("cuitViejo");
            String tipoJuridico = context.formParam("tipoJuridico");
            String rubro = context.formParam("rubro");

            /*if ( !cuit.matches("[0-9]{11}") )  {
                notificacionCambio.error("El campo CUIT es obligatorio y debe tener 11 dígitos");
                context.sessionAttribute("notificacionCambio", notificacionCambio);
                context.redirect("/configurarPerfil");
                return;
            }*/

            if ( !tipoJuridico.equals("0") ) {
                TipoJuridico tipo;
                switch (tipoJuridico) {
                    case "1" -> tipo = TipoJuridico.GUBERNAMENTAL;
                    case "2" -> tipo = TipoJuridico.ONG;
                    case "3" -> tipo = TipoJuridico.EMPRESA;
                    case "4" -> tipo = TipoJuridico.INSTITUCION;
                    default -> tipo = null;
                }
                personaModificada.setTipoJuridico(tipo);
            }

            if ( !rubro.equals("0") ) {
                Rubro rubro1;
                switch (tipoJuridico) {
                    case "1" -> rubro1 = Rubro.GASTRONOMIA;
                    case "2" -> rubro1 = Rubro.ELECTRONICA;
                    case "3" -> rubro1 = Rubro.ARTICULOS_HOGAR;
                    default -> rubro1 = null;
                }
                personaModificada.setRubro(rubro1);
            }

            personaModificada.setRazonSocial(nombre);
            //personaModificada.setCUIT(cuit);
            personaModificada.setTelefono(telefono);
            personaModificada.setDireccion(direccion);
            personaModificada.setEmail(email);
            repoColaboradores.actualizarPersona(personaModificada);
        }

        notificacionCambio.aprobada("Perfil actualizado correctamente");
        context.sessionAttribute("notificacionCambio", notificacionCambio);
        context.redirect("/perfil");

    }
}
@Getter
@Setter
class NotificacionCambio{
    private String tipo;
    private String mensaje;

    public void aprobada(String mensaje){
        this.mensaje = mensaje;
        this.tipo = "success";
    }

    public void error(String mensaje){
        this.mensaje = mensaje;
        this.tipo = "danger";
    }
}
