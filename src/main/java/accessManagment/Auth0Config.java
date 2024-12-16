package accessManagment;

public class Auth0Config {

    public static final String CLIENT_ID = "eWCr7VkPua7LBG6comgq7MimjwwdYcuT";
    public static final String CLIENT_SECRET = "xrMBTcwN60sGHDJJW7lOXAaMQD2c8qne4LRg6vUl4mBaoSx6raXruP5HlKaV68Jo";
    public static final String DOMAIN = "dev-qujwns514tnar8lw.us.auth0.com";
    public static final String BASE_URL = "https://comunidades-solidarias.onrender.com"; // Cambiar según el entorno
    public static final String REDIRECT_URI = BASE_URL + "/loginSSO"; // Ajustar al endpoint que maneja el callback

    // Genera la URL de autorización
    public static String getAuthorizationUrl() {
        return String.format(
                "https://%s/authorize?response_type=code&client_id=%s&redirect_uri=%s&scope=openid profile email&prompt=login",
                DOMAIN, CLIENT_ID, REDIRECT_URI);
    }

    // Genera la URL para obtener el token
    public static String getTokenUrl() {
        return String.format("https://%s/oauth/token", DOMAIN);
    }

    // Genera la URL de logout
    public static String getLogoutUrl() {
        return String.format(
                "https://%s/v2/logout?client_id=%s&returnTo=%s",
                DOMAIN, CLIENT_ID, BASE_URL);
    }
}
