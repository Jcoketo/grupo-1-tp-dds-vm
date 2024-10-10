import io.javalin.Javalin;
import persistencia.RepositorioColaboradores;
import presentacion.*;

public class Application {

    public static void main(String[] args) {

        Javalin app = Javalin.create(javalinConfig -> {
                    javalinConfig.plugins.enableCors(cors -> {
                        cors.add(it -> it.anyHost());
                    }); // para poder hacer requests de un dominio a otro

                    javalinConfig.staticFiles.add("/"); //recursos estaticos (HTML, CSS, JS, IMG)
                }

            )
            .get("/home", ctx -> ctx.result("Hello World"))
            .start(8080);

        RepositorioColaboradores repoColab = RepositorioColaboradores.getInstancia();

        app.get("/login", new ShowLoginController());
        app.post("/login", new ProcessLoginController(repoColab));

        app.get("/inicio-sinLog", new InicioController());
        app.get("/inicio-conLog", new InicioLogeadoController());
        app.get("/elegirRegistroCuenta", new ElegirRegistroCuentaController());
        app.get("/crearCuentaJuridica", new CrearCuentaJuridicaController());
        app.get("/crearCuentaFisica", new CrearCuentaFisicaController());
        app.post("/crearCuentaFisica", new CuentaFisicaCreadaController());
        app.post("/crearCuentaJuridica", new CuentaJuridicaCreadaController());

        // http://localhost:8080/home

        // API REST
        //app.get("/api/mascotas", new GetMascotasHandler());
        //app.get("/api/mascotas/{id}", new GetMascotaIdHandler());
        //app.get("/api/mascotas/{id}/imagen", new GetMascotaImgHandler());
        //app.post("/api/mascotas", new PostMascotaHandler());
        //app.get("/api/mis-datos", new GetPerfilSesionHandler());

        //app.post("/api/login", new LoginHandler());

        // VISTA
        //app.get("/home", new IndexHandler());
        //app.get("/info-mascota", new InfoMascotaHandler());


        app.exception(IllegalArgumentException.class, (e, ctx) -> {
            ctx.status(400);
        });

    }
}
