package presentacion.gestorCuentas;

import java.util.Map;

import modelo.excepciones.ExcepcionValidacion;
import modelo.validador.Usuario;
import org.jetbrains.annotations.NotNull;

import accessManagment.Roles;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.personas.TipoPersona;
import persistencia.RepositorioColaboradores;
import persistencia.RepositorioUsuarios;
import modelo.authService.AuthServiceUsuario;
import persistencia.RepositoriosTecnicos;
import utils.GeneradorModel;

public class ProcessLoginController implements Handler {

    private RepositorioColaboradores repoColab = RepositorioColaboradores.getInstancia();
    private RepositorioUsuarios repoUsuarios = RepositorioUsuarios.getInstancia();
    private RepositoriosTecnicos repoTecnicos = RepositoriosTecnicos.getInstancia();


    public ProcessLoginController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        String email = context.formParam("email");
        String password = context.formParam("password");

        if (email == null || email.equals("") || password == null || password.equals("")) {
            context.sessionAttribute("errorLogin", "Faltan completar campos obligatorios");
            context.redirect("/login");
            return;
        }

        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            context.sessionAttribute("errorLogin", "El mail no es valido");
            context.redirect("/login");
            return;
        }
        /*
        try {
            email = ESAPI.validator().getValidInput("Email", email, "Email", 200, false);
            password = ESAPI.validator().getValidInput("Password", password, "SafeString", 200, false);
        } catch (ValidationException e) {
            context.sessionAttribute("errorLogin", "El mail o la contraseña no son válidos");
            context.redirect("/login");
            return;
        }*/

        try {
            AuthServiceUsuario.autenticarUsuario(email, password);
        } catch (ExcepcionValidacion e) {
            context.sessionAttribute("errorLogin", "El mail o la contraseña son incorrectos");
            context.redirect("/login");
            return;
        }

        context.sessionAttribute("logueado", true);


        context.sessionAttribute("mailUsuario",email);

        Usuario usuario = repoUsuarios.traerUsuario(email);

        if ( usuario == null ) { // no deberia suceder
            context.sessionAttribute("errorLogin", "No se encontro el usuario");
            context.redirect("/login");
            return;
        }
        context.sessionAttribute("idPersona", usuario.getPersona().getId());
        context.sessionAttribute("rolUsuario", usuario.getRol());
        context.sessionAttribute("nombreUsuario", usuario.getUsername());


        TipoPersona tipoPer = repoColab.devolverTipoPersona(usuario.getPersona().getId());
        context.sessionAttribute("tipoPersona", tipoPer);

        context.sessionAttribute("validado", false);
        if ( usuario.getUsername() == null ||  usuario.getUsername().equals("") ) {
            context.sessionAttribute("mail", email);
            context.redirect("/validarDatos");
            return;
        }
        context.sessionAttribute("validado", true);

        if (usuario.getRol() == Roles.ADMIN){
            context.sessionAttribute("esAdmin", true);
            context.redirect("/inicioAdmin");
            return;
        }

        if (usuario.getRol() == Roles.TECNICO){
            Integer idTecnico = repoTecnicos.buscarTecnicoXIdPersona(usuario.getPersona().getId()).getId();
            if ( idTecnico == null ) {
                context.sessionAttribute("errorLogin", "No se encontro el tecnico");
                context.redirect("/login");
                return; }
            context.redirect("/inicioTecnico");
            context.sessionAttribute("idTecnico", idTecnico);

            return;
        }

        context.redirect("/inicio");

    }


}
