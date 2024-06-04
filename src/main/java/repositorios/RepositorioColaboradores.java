package repositorios;

import personas.Colaborador;

import java.util.ArrayList;
import java.util.List;

public class RepositorioColaboradores {
    private static RepositorioColaboradores instancia = null;

    private static List<Colaborador> colaboradores;

    private RepositorioColaboradores() {
        colaboradores = new ArrayList<Colaborador>();
    }
    public static RepositorioColaboradores getInstancia() {
        if(instancia == null) {
            instancia = new RepositorioColaboradores();
        }
        return instancia;
    }

    public Boolean conocerExistencia(Colaborador colaborador) {
        //TODO
        return false;
    }

    public void agregar(Colaborador colaborador) {
        colaboradores.add(colaborador);
    }

}

