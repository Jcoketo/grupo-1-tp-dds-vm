package presentacion.gestorCuentas;

import accessManagment.Roles;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.authService.AuthServiceUsuario;
import modelo.personas.Persona;
import modelo.personas.PersonaHumana;
import modelo.personas.Tecnico;
import modelo.personas.TipoDocumento;
import modelo.validador.Usuario;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioColaboradores;
import persistencia.RepositorioUsuarios;
import persistencia.RepositoriosTecnicos;
import utils.GeneradorModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class ValidarDatosFinalizadoController implements Handler {

    private static RepositorioUsuarios repositorioUsuarios = RepositorioUsuarios.getInstancia();
    private static RepositorioColaboradores repoColaboradores = RepositorioColaboradores.getInstancia();
    private static RepositoriosTecnicos repoTecnicos = RepositoriosTecnicos.getInstancia();

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        Integer idPersona = context.sessionAttribute("idPersona");
        String mailOriginal = context.sessionAttribute("mail");
        Roles rol = context.sessionAttribute("rolUsuario");

        String mailNuevo = context.formParam("mail");
        String username = context.formParam("username");
        String password = context.formParam("password");
        String nombre = context.formParam("nombre");
        String apellido = context.formParam("apellido");
        String tipoDoc = context.formParam("tipoDoc");
        String numeroDoc = context.formParam("numeroDoc");
        String telefono = context.formParam("telefono");
        String direccion = context.formParam("direccion");
        String fNac = context.formParam("fechaNacimiento");
        String cuil = context.formParam("cuil");

        if (nombre.equals("") || apellido.equals("") || username.equals("") || password.equals("") || numeroDoc.equals("")
                || mailNuevo.equals("") )  {
            model.put("errorValidador", "Los campos nombre, apellido y fecha de nacimiento son obligatorios");
            context.redirect("/validarDatos");
            return;
        }
        if ( rol == Roles.TECNICO && ( cuil.equals("") || direccion.equals("") && !esNumerico(cuil) ) )  {
            model.put("errorValidador", "El campo CUIT y direccion es obligatorio");
            context.redirect("/validarDatos");
            return;
        }

        if ( !mailOriginal.equals(mailNuevo) || mailNuevo.equals("") )  {
            if ( !mailNuevo.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$") )  {
                model.put("errorValidador", "El mail nuevo ingresado no es valido");
                context.redirect("/validarDatos");
                return;
            }
        }

        if ( !esNumerico(numeroDoc)  ||  (!telefono.equals("") && !esNumerico(telefono)) ) {
            model.put("error", "El número de documento o el teléfono no son numéricos");
            context.redirect("/validarDatos");
            return;
        }

        if ( !numeroDoc.matches("[0-9]{0,8}") )  {
            model.put("error", "El número de documento debe tener 8 dígitos");
            context.redirect("/validarDatos");
            return;
        }

        if ( !cuil.equals("") && !cuil.matches("[0-9]{11}") )  {
            model.put("errorValidador", "El campo CUIT es obligatorio y debe tener 11 dígitos");
            context.redirect("/validarDatos");
            return;
        }

        if ( !telefono.equals("")  &&  !telefono.matches("[0-9]{8,10}") )  {
            model.put("errorValidador", "El teléfono debe tener entre 8 y 10 dígitos");
            context.redirect("/validarDatos");
            return;
        }

        if ( repositorioUsuarios.existeMAIL(mailNuevo) )  {
            model.put("errorValidador", "El mail ya se encuentra registrado");
            context.redirect("/validarDatos");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaNacimiento = LocalDate.parse(fNac, formatter);

        Usuario usuario = repositorioUsuarios.traerUsuario(mailOriginal);
        PersonaHumana persona = repoColaboradores.traerPersonaPorIdFisica(idPersona);

        if ( !tipoDoc.equals("0") ) {
            TipoDocumento tipoDocumentoEnum;
            switch (tipoDoc) {
                case "1" -> tipoDocumentoEnum = TipoDocumento.DNI;
                case "2" -> tipoDocumentoEnum = TipoDocumento.LC;
                case "3" -> tipoDocumentoEnum = TipoDocumento.LE;
                default -> tipoDocumentoEnum = TipoDocumento.DNI;
            }
            persona.getDocumento().setTipoDoc(tipoDocumentoEnum);
        }

        persona.setNombre(nombre);
        persona.setApellido(apellido);
        persona.setFechaNacimiento(fechaNacimiento);
        persona.getDocumento().setNumeroDoc(numeroDoc);
        persona.setDireccion(direccion);
        persona.setTelefono(telefono);
        persona.setEmail(mailNuevo);

        usuario.setMail(mailNuevo);
        usuario.setUsername(username);
        password = AuthServiceUsuario.hashPassword(password);
        usuario.setHashedPassword(password);

        repositorioUsuarios.persistirUsuario(usuario);
        repoColaboradores.actualizarPersona(persona);
        if ( rol == Roles.TECNICO ) {
            Tecnico tecnico = repoTecnicos.buscarTecnicoXIdPersona(idPersona);
            tecnico.setNroCUIL(cuil);
            repoTecnicos.registrarTecnico(tecnico);
        }

        context.redirect("/inicio");

    }

    public static boolean esNumerico(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
