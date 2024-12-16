import io.javalin.Javalin;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.prometheus.PrometheusMeterRegistry;

public class MetricsManager {

    private final PrometheusMeterRegistry prometheusMeterRegistry;
    private final Counter requestCounter;

    public MetricsManager() {
        // Crear el registry de Prometheus
        prometheusMeterRegistry = new PrometheusMeterRegistry(io.micrometer.prometheus.PrometheusConfig.DEFAULT);

        // Registrar un contador de solicitudes
        requestCounter = Counter.builder("app_requests_total")
                .description("Total number of requests")
                .register(prometheusMeterRegistry);

        // Registrar una métrica de ejemplo (usuarios activos)
        Gauge.builder("active_users", this::getActiveUsers)
                .description("Number of active users")
                .register(prometheusMeterRegistry);
    }

    // Retorna el número de usuarios activos (esto puede ser dinámico según tu lógica)
    private double getActiveUsers() {
        return 10; // Cambia este valor con la lógica real
    }

    public void configureMetricsEndpoint(Javalin app) {
        // Exponer el endpoint de métricas
        app.get("/metrics", ctx -> ctx.result(prometheusMeterRegistry.scrape()));
    }

    public void incrementRequestCounter() {
        requestCounter.increment();
    }

    public void configureRequestCounter(Javalin app) {
        // Incrementar el contador con cada solicitud
        app.before(ctx -> incrementRequestCounter());
    }
}

