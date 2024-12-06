package persistencia;

import lombok.Getter;
import modelo.excepciones.ExcepcionValidacion;
import modelo.importador.RegistroLeido;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class RepositorioArchivos {
    @Getter
    private static RepositorioArchivos instancia = null;

    private List<List<RegistroLeido>> CSVProcesados;
    private List<List<RegistroLeido>> CSVNOProcesados;

    private RepositorioArchivos(EntityManager entityManager) {
        CSVProcesados = new ArrayList<>();
        CSVNOProcesados = new ArrayList<>();
    }
    // TODO ENORME

    public static RepositorioArchivos getInstancia(EntityManager entityManager) {
        if(instancia == null) {
            instancia = new RepositorioArchivos(entityManager);
        }
        return instancia;
    }
    public static RepositorioArchivos getInstancia() {
        if(instancia == null) {
            throw new ExcepcionValidacion("No fue instanciado en el repositorio!");
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