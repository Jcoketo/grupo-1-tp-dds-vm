import static io.javalin.apibuilder.ApiBuilder.*;

import accessManagment.AutorizacionMiddleware;
import io.javalin.Javalin;
import persistencia.RepositorioColaboradores;
import persistencia.RepositorioHeladeras;
import persistencia.RepositorioIncidentes;
import persistencia.RepositorioOfertas;
import persistencia.RepositorioPersonasVulnerables;
import presentacion.InicioController;
import presentacion.colaboraciones.DonarDineroController;
import presentacion.colaboraciones.DonarDineroRealizadaController;
import presentacion.colaboraciones.DonarDistribucionViandaController;
import presentacion.colaboraciones.DonarViandaController;
import presentacion.colaboraciones.DonarViandaRealizadaController;
import presentacion.colaboraciones.ElegirDonacionController;
import presentacion.colaboraciones.ElegirDonacionFisicaController;
import presentacion.colaboraciones.ElegirDonacionJuridicaController;
import presentacion.colaboraciones.GraciasPorDonarController;
import presentacion.colaboraciones.MisColaboracionesController;
import presentacion.colaboraciones.RegistroPersonaVulnerableController;
import presentacion.colaboraciones.RegistroPersonaVulnerableFinalController;
import presentacion.colaboraciones.RegistroPersonaVulnerableRealizadaController;
import presentacion.gestorCuentas.CrearCuentaFisicaController;
import presentacion.gestorCuentas.CrearCuentaJuridicaController;
import presentacion.gestorCuentas.CuentaCreadaController;
import presentacion.gestorCuentas.CuentaFisicaCreadaController;
import presentacion.gestorCuentas.CuentaJuridicaCreadaController;
import presentacion.gestorCuentas.ElegirRegistroCuentaController;
import presentacion.gestorCuentas.InicioLogueadoController;
import presentacion.gestorCuentas.PerfilController;
import presentacion.gestorCuentas.ProcessLoginController;
import presentacion.gestorCuentas.ShowLoginController;
import presentacion.heladera.AceptarAgregarHeladeraController;
import presentacion.heladera.AgregarHeladeraController;
import presentacion.heladera.HeladeraAgregadaController;
import presentacion.heladera.MapaHeladeraVistaController;
import presentacion.heladera.MapaHeladerasController;
import presentacion.heladera.MapaHeladerasDistribucionDestinoController;
import presentacion.heladera.MapaHeladerasDistribucionOrigenController;
import presentacion.heladera.VisualizarDetalleHeladeraController;
import presentacion.incidentes.AceptarReportarFallaController;
import presentacion.incidentes.ReportarFallaTecnicaController;
import presentacion.incidentes.ReportarFallaTecnicaFinalizadaController;
import presentacion.incidentes.VisualizarAlertasController;
import presentacion.incidentes.VisualizarFallasTecnicasController;
import presentacion.ofertas.AceptarAgregarProductoController;
import presentacion.ofertas.AgregarProductosEmpresaController;
import presentacion.ofertas.AgregarProductosEmpresaFinalizadoController;
import presentacion.ofertas.CanjearPuntosController;
import presentacion.ofertas.CatalogoProductosController;
import presentacion.reportes.MisReportesController;

public class Router {
    private static Javalin app = Application.app();

    public static void init(){
        /* *************************************************************************** */
        RepositorioIncidentes repoIncidentes = RepositorioIncidentes.getInstancia();
        RepositorioColaboradores repoColab = RepositorioColaboradores.getInstancia();
        RepositorioHeladeras repoHeladeras = RepositorioHeladeras.getInstancia();
        RepositorioPersonasVulnerables repoPersonasVulnerable = RepositorioPersonasVulnerables.getInstancia();
        RepositorioOfertas repoOfertas = RepositorioOfertas.getInstancia();
        /* *************************************************************************** */

        // ------------------------------------   RUTAS   ------------------------------------------------
        app.routes(() -> {

            path("/login", () -> {
                get(new ShowLoginController());
                post(new ProcessLoginController(repoColab));
            });

            path("/inicio", () -> {
                get(new InicioController());
            });

            // no deberia estar ´porque verificamos el log en los mustache
            /*path("/inicioLogueado", () -> {
                get(new InicioLogueadoController());
            });*/

            path("/elegirRegistroCuenta", () -> {
                before(new AutorizacionMiddleware());
                get(new ElegirRegistroCuentaController());
            });

            path("/crearCuentaJuridica", () -> {
                //before(new AutorizacionMiddleware());
                get(new CrearCuentaJuridicaController());
                post(new CuentaJuridicaCreadaController(repoColab));
            });

            path("/crearCuentaFisica", () -> {
                before(new AutorizacionMiddleware());
                get(new CrearCuentaFisicaController());
                post(new CuentaFisicaCreadaController(repoColab));
            });

            path("/cuentaCreada", () -> {
                before(new AutorizacionMiddleware());
                get(new CuentaCreadaController());
            });

            //TODO todos los botones que redireccionen a DONAR tienen que venir a este GET
            // TODO FALTA INSERTAR LAS IMAGENES EN LOS BOTONES DE ELEGIR DONACION (en mustache)
            path("/elegirDonacion", () -> {
                before(new AutorizacionMiddleware().setDebeSerLogueado());
                get(new ElegirDonacionController());
            });

            path("/elegirDonacionFisica", () -> {
                before(new AutorizacionMiddleware().setDebeSerLogueado());
                get(new ElegirDonacionFisicaController());
            });

            path("/elegirDonacionJuridica", () -> {
                before(new AutorizacionMiddleware().setDebeSerLogueado());
                get(new ElegirDonacionJuridicaController());
            });

            path("/donarVianda", () -> {
                before(new AutorizacionMiddleware().setDebeSerLogueado().setDebeSerPF());
                get(new DonarViandaController());
                post(new DonarViandaRealizadaController(repoHeladeras));
            });

            path("/donarDinero", () -> {
                before(new AutorizacionMiddleware().setDebeSerLogueado());
                get(new DonarDineroController());
                post(new DonarDineroRealizadaController());
            });

            path("/donarDistribuirViandas", () -> {
                before(new AutorizacionMiddleware().setDebeSerLogueado().setDebeSerPF());
                get(new DonarDistribucionViandaController());
                //post(new DonarDistribucionViandaRealizadaController());
            });

            path("/graciasPorDonar", () -> {
                before(new AutorizacionMiddleware().setDebeSerLogueado());
                get(new GraciasPorDonarController());
            });

            path("/agregarHeladera", () -> {
                before(new AutorizacionMiddleware().setDebeSerLogueado().setDebeSerPJ());
                get(new AgregarHeladeraController());
                post(new HeladeraAgregadaController(repoHeladeras));
            });

            path("/aceptarAgregarHeladera", () -> {
                before(new AutorizacionMiddleware().setDebeSerLogueado().setDebeSerPJ());
                get(new AceptarAgregarHeladeraController());
            });

            path("/mapaHeladeras", () -> {
                get(new MapaHeladeraVistaController());
            });

            path("/mapaHeladerasDistribucionOrigen", () -> {
                get(new MapaHeladerasDistribucionOrigenController());
            });

            path("/mapaHeladerasDistribucionDestino", () -> {
                get(new MapaHeladerasDistribucionDestinoController());
            });

            path("/mapaHeladerasRequest", () -> {
                get(new MapaHeladerasController(repoHeladeras));
            });

            path("/visualizarDetalleHeladera", () -> {
                get(new VisualizarDetalleHeladeraController());
            });

            path("/visualizarAlertas", () -> {
                before(new AutorizacionMiddleware().setDebeSerLogueado());
                get(new VisualizarAlertasController());
            });

            path("/visualizarFallasTecnicas", () -> {
                before(new AutorizacionMiddleware().setDebeSerLogueado());
                get(new VisualizarFallasTecnicasController());
            });

            path("/registroPersonaVulnerable", () -> {
                before(new AutorizacionMiddleware().setDebeSerLogueado());
                get(new RegistroPersonaVulnerableController());
                post(new RegistroPersonaVulnerableRealizadaController(repoPersonasVulnerable));
            });

            path("/registroPersonaVulnerableFinal", () -> {
                before(new AutorizacionMiddleware().setDebeSerLogueado());
                get(new RegistroPersonaVulnerableFinalController());
            });

            path("/reportarFallaTecnica", () -> {
                before(new AutorizacionMiddleware().setDebeSerLogueado());
                get(new ReportarFallaTecnicaController());
                post(new ReportarFallaTecnicaFinalizadaController(repoIncidentes));
            });

            path("/aceptarReportarFalla", () -> {
                before(new AutorizacionMiddleware().setDebeSerLogueado());
                get(new AceptarReportarFallaController());
            });

            path("/canjearPuntos", () -> {
                before(new AutorizacionMiddleware().setDebeSerLogueado());
                get(new CanjearPuntosController());
            });

            path("/catalogoProductos", () -> {
                before(new AutorizacionMiddleware().setDebeSerLogueado());
                get(new CatalogoProductosController());
            });

            path("/agregarProductosEmpresa", () -> {
                before(new AutorizacionMiddleware().setDebeSerLogueado());
                get(new AgregarProductosEmpresaController());
                post(new AgregarProductosEmpresaFinalizadoController(repoOfertas));
            });

            path("/aceptarAgregarProducto", () -> {
                before(new AutorizacionMiddleware().setDebeSerLogueado());
                get(new AceptarAgregarProductoController());
            });

            path("/misReportes", () -> {
                before(new AutorizacionMiddleware().setDebeSerLogueado());
                get(new MisReportesController());
            });

            path("/perfil", () -> {
                before(new AutorizacionMiddleware().setDebeSerLogueado());
                get(new PerfilController());
            });

            path("/misColaboraciones", () -> {
                before(new AutorizacionMiddleware().setDebeSerLogueado());
                get(new MisColaboracionesController());
            });

            path("/404Error", () -> {
                //get(new MostrarErrorPermisosController()); //TODO
            });

        });

        // EXCEPCION
        app.exception(IllegalArgumentException.class, (e, ctx) -> {
            ctx.status(400);
        });
    }
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