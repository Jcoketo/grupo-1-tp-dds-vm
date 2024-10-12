import io.javalin.Javalin;
import modelo.elementos.Heladera;
import modelo.elementos.PuntoEstrategico;
import persistencia.RepositorioColaboradores;
import persistencia.RepositorioHeladeras;
import persistencia.RepositorioIncidentes;
import persistencia.RepositorioPersonasVulnerables;
import presentacion.*;

import java.time.LocalDate;

public class Router {
    private static Javalin app = Application.app();

    public static void init(){
        // ACA INSTANCIAMOS OBJETOS PARA PROBAR Y VISUALIZAR COMO SE VEN EN LA WEB
        /* *************************************************************************** */
        RepositorioIncidentes repoIncidentes = RepositorioIncidentes.getInstancia();
        RepositorioColaboradores repoColab = RepositorioColaboradores.getInstancia();
        RepositorioHeladeras repoHeladeras = RepositorioHeladeras.getInstancia();
        RepositorioPersonasVulnerables repoPersonasVulnerable = RepositorioPersonasVulnerables.getInstancia();


        /* *************************************************************************************************** */
        PuntoEstrategico puntoEstrategico1 = new PuntoEstrategico(-34.5986, -58.4208);
        PuntoEstrategico puntoEstrategico2 = new PuntoEstrategico(-34.5986, -58.4208);

        puntoEstrategico1.setDireccion("Heladera Medrano UTN");
        puntoEstrategico2.setDireccion("Heladera Lugano UTN");

        LocalDate fecha = LocalDate.now();

        Heladera heladeraPrueba1 = new Heladera(1,fecha, puntoEstrategico1);
        Heladera heladeraPrueba2 = new Heladera(1,fecha, puntoEstrategico2);

        heladeraPrueba1.setNombre("Heladera Medrano UTN");
        heladeraPrueba2.setNombre("Heladera Lugano UTN");

        repoHeladeras.agregarHeladera(heladeraPrueba1);
        repoHeladeras.agregarHeladera(heladeraPrueba2);
        /* *************************************************************************** */

        // ------------------------------------   RUTAS   ------------------------------------------------
        app.get("/login", new ShowLoginController()/*, (RouteRole) Set.of(Roles.SIN_PERMISOS)*/);
        app.post("/login", new ProcessLoginController(repoColab));

        app.get("/inicio-sinLog", new InicioController());
        app.get("/inicio-conLog", new InicioLogeadoController());

        // Routa para probar los Roles
        // app.get("/elegirRegistroCuenta", new ElegirRegistroCuentaController(), (RouteRole) Set.of(Roles.SIN_PERMISOS));
        app.get("/elegirRegistroCuenta", new ElegirRegistroCuentaController());

        app.get("/crearCuentaJuridica", new CrearCuentaJuridicaController());
        app.get("/crearCuentaFisica", new CrearCuentaFisicaController());

        app.post("/crearCuentaFisica", new CuentaFisicaCreadaController(repoColab));
        app.post("/crearCuentaJuridica", new CuentaJuridicaCreadaController(repoColab));
        app.get("/cuentaCreada", new CuentaCreadaController());


        //TODO todos los botones que redireccionen a DONAR tienen que venir a este GET
        // TODO FALTA INSERTAR LAS IMAGENES EN LOS BOTONES DE ELEGIR DONACION (en mustache)
        app.get("/elegirDonacion", new ElegirDonacionController());
        app.get("/elegirDonacionFisica", new ElegirDonacionFisicaController());
        app.get("/elegirDonacionJuridica", new ElegirDonacionJuridicaController());

        //app.get("/donarVianda/{nombre}&{direccion}&{estado}&{disponibilidad}", new DonarViandaController());
        app.get("/donarVianda", new DonarViandaController());

        app.post("/donarVianda", new DonarViandaRealizadaController(repoHeladeras));

        app.get("/donarDinero", new DonarDineroController());
        app.post("/donarDinero", new DonarDineroRealizadaController());

        app.get("/donarDistribuirViandas", new DonarDistribucionViandaController());
        //app.post("/donarDistribuirViandas", new DonarDistribucionViandaRealizadaController());

        app.get("/donarEntregarTarjetas", new DonarEntregarTarjetasController());
        //app.post("/donarEntregarTarjetas", new DonarEntregarTarjetasRealizadaController());

        app.get("/graciasPorDonar", new GraciasPorDonarController());

        app.get("/agregarHeladera", new AgregarHeladeraController());
        app.post("/heladeraAgregada", new HeladeraAgregadaController(repoHeladeras));


        app.get("/mapaHeladeras", new MapaHeladeraVistaController());


        app.get("/mapaHeladerasRequest", new MapaHeladerasController(repoHeladeras));

        app.get("/visualizarDetalleHeladera", new VisualizarDetalleHeladeraController());

        app.get("/visualizarAlertas", new VisualizarAlertasController());

        app.get("/visualizarFallasTecnicas", new VisualizarFallasTecnicasController());

        app.get("/registroPersonaVulnerable", new RegistroPersonaVulnerableController());
        app.post("/registroPersonaVulnerable", new RegistroPersonaVulnerableRealizadaController(repoPersonasVulnerable));

        app.get("/registroPersonaVulnerableFinal", new RegistroPersonaVulnerableFinalController());

        app.get("/reportarFallaTecnica", new ReportarFallaTecnicaController());
        app.post("/reportarFallaTecnica", new ReportarFallaTecnicaFinalizadaController(repoIncidentes));
        //app.get("/aceptarReportarFalla", new AceptarReportarFallaController());


        // EXCEPCION
        app.exception(IllegalArgumentException.class, (e, ctx) -> {
            ctx.status(400);
        });
    }

    /* Función para obtener el rol del contexto
    public static Roles getUserRole(Context ctx) {
        return Roles.CON_PERMISOS;//ctx.sessionAttribute("rolUsuario");
    }*/
}

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