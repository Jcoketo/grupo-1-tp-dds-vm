import io.javalin.Javalin;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class Application {
    private static Javalin app = null;

    public static Javalin app() {
        if(app == null)
            throw new RuntimeException("App no inicializada");
        return app;
    }

    public static void main(String[] args) {
        app = Javalin.create(javalinConfig -> {
                    javalinConfig.plugins.enableCors(cors -> {
                        cors.add(it -> it.anyHost());
                    }); // para poder hacer requests de un dominio a otro

                    // javalinConfig.staticFiles.add("/"); //recursos estaticos (HTML, CSS, JS, IMG)

                    /*javalinConfig.accessManager((handler, ctx, routeRoles) -> {
                        Roles userRole = getUserRole(ctx);
                        if (routeRoles.contains(userRole)) {
                            handler.handle(ctx);
                        }
                        else {
                            ctx.status(401).result("Unauthorized");
                            //ctx.redirect("/login");
                        }
                    });*/
                })
            .start(8080);

        //Inicializamos el router:
        Router.init(getEntityManager());

    }
    private static EntityManager getEntityManager(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
        return emf.createEntityManager();
    }



}