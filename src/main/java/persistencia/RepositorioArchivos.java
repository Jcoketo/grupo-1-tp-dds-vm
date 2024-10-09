package persistencia;

import modelo.importador.RegistroLeido;
import java.util.ArrayList;
import java.util.List;

public class RepositorioArchivos {
    private static RepositorioArchivos instancia = null;

    private List<List<RegistroLeido>> CSVProcesados;
    private List<List<RegistroLeido>> CSVNOProcesados;

    private RepositorioArchivos() {
        CSVProcesados = new ArrayList<>();
        CSVNOProcesados = new ArrayList<>();
    }

    public static RepositorioArchivos getInstancia() {
        if(instancia == null) {
            instancia = new RepositorioArchivos();
        }
        return instancia;
    }

    public void agregarCSVNoProcesado(List<RegistroLeido> registros) {
        CSVNOProcesados.add(registros);
    }

    public void agregarCSVProcesado(List<RegistroLeido> registros) {
        CSVProcesados.add(registros);
    }

    public void cambiarEstadoAProcesado(List<RegistroLeido> registros) {
        CSVNOProcesados.remove(registros);
        CSVProcesados.add(registros);
    }

    public List<RegistroLeido> tomarPorIndice(Integer index) {
        return CSVNOProcesados.get(index);
    }
}