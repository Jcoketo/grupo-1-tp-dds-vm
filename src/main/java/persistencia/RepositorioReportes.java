package persistencia;

import java.util.ArrayList;
import java.util.List;
import modelo.reportador.Reporte;

public class RepositorioReportes {
    private List<Reporte> reportes;
    private static RepositorioReportes instancia = null;


    public RepositorioReportes() {
        this.reportes = new ArrayList<>();
    }

    public static RepositorioReportes getInstancia() {
        if(instancia == null) {
            instancia = new RepositorioReportes();
        }
        return instancia;
    }

    public void agregarReporte(Reporte reporte) {
        this.reportes.add(reporte);
    }

    public List<Reporte> obtenerReportes() {
        return this.reportes;
    }
}