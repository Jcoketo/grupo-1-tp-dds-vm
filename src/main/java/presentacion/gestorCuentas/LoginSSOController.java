package presentacion.gestorCuentas;

import accessManagment.Auth0Config;
import accessManagment.Roles;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.personas.TipoPersona;
import modelo.validador.Usuario;
import persistencia.RepositorioColaboradores;
import persistencia.RepositorioUsuarios;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class LoginSSOController implements Handler {

    private RepositorioUsuarios repoUsuarios = RepositorioUsuarios.getInstancia();
    private RepositorioColaboradores repoColab = RepositorioColaboradores.getInstancia();

    @Override
    public void handle(@NotNull Context context) throws Exception {
        String authCode = context.queryParam("code");

        // Paso 1: Redirigir a Auth0 si no hay código de autorización
        if (authCode == null) {
            String authUrl = Auth0Config.getAuthorizationUrl();
            context.redirect(authUrl);
            return;
        }

        String tokenResponse = exchangeCodeForToken(authCode);

        String userInfo = parseIdToken(tokenResponse);

        String email = obtenerMail(userInfo);
        String nombre = obtenerNombre(userInfo);
        String apellido = obtenerApellido(userInfo);

        // Paso 5: Verificar si el usuario existe en la base de datos
        if (!repoUsuarios.existeMAIL(email)) { // ES UN NUEVO REGISTRO
            // Redirigir al registro si el usuario no existe
            context.sessionAttribute("auth0Email", email);
            context.sessionAttribute("auth0Nombre", nombre);
            context.sessionAttribute("auth0Apellido", apellido);
            context.sessionAttribute("registroAuth0",true);
            context.redirect("/elegirRegistroCuenta");
            return;
        }

        // Paso 6: Configurar sesión para usuarios existentes
        configurarYEnviar(context, email);

    }

    // Intercambiar código de autorización por un token
    private String exchangeCodeForToken(String code) throws Exception {
        String params = "grant_type=authorization_code"
                + "&client_id=" + Auth0Config.CLIENT_ID
                + "&client_secret=" + Auth0Config.CLIENT_SECRET
                + "&code=" + code
                + "&redirect_uri=" + Auth0Config.REDIRECT_URI;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Auth0Config.getTokenUrl()))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(params))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new Exception("Error al obtener el token: " + response.body());
        }
    }

    // Decodificar el id_token de Auth0
    private String parseIdToken(String tokenResponse) {
        String idToken = tokenResponse.split("\"id_token\":\"")[1].split("\"")[0];
        String[] jwtParts = idToken.split("\\.");
        return new String(Base64.getUrlDecoder().decode(jwtParts[1]));
    }

    // Extraer el email del token decodificado
    private String obtenerMail(String userInfo) {
        // Usa una librería JSON (por ejemplo, Jackson o Gson) para parsear correctamente
        return userInfo.split("\"email\":\"")[1].split("\"")[0];
    }

    // Extraer el nombre del usuario (si está presente)
    private String obtenerNombre(String userInfo) {
        try {
            return userInfo.split("\"given_name\":\"")[1].split("\"")[0];
        } catch (Exception e) {
            return null; // Nombre no proporcionado en el token
        }
    }

    // Extraer el apellido del usuario (si está presente)
    private String obtenerApellido(String userInfo) {
        try {
            return userInfo.split("\"family_name\":\"")[1].split("\"")[0];
        } catch (Exception e) {
            return null; // Apellido no proporcionado en el token
        }
    }

    // Configurar sesión para el usuario existente
    private void configurarYEnviar(Context context, String email) {
        context.sessionAttribute("logueado", true);

        Usuario usuario = repoUsuarios.traerUsuario(email);
        context.sessionAttribute("rolUsuario", usuario.getRol());
        context.sessionAttribute("nombreUsuario", usuario.getUsername());

        TipoPersona tipoPer = repoColab.devolverTipoPersona(usuario.getPersona().getId());
        context.sessionAttribute("tipoPersona", tipoPer);

        context.sessionAttribute("idPersona", usuario.getPersona().getId());


        if (usuario.getRol() == Roles.ADMIN){
            context.redirect("/inicioADMIN");
            return;
        }
        if(usuario.getRol() == Roles.TECNICO){
            context.redirect("/inicioTecnico");
            return;
        }

        context.redirect("/inicio");

    }
}
