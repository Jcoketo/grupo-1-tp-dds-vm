package repositorios;

import personas.Tecnico;

import java.util.List;

public class RepositoriosTecnicos{
    private static RepositoriosTecnicos instancia = null;
    private List<Tecnico> tecnicos;

    public void agregar(Tecnico tecnico){
        tecnicos.add(tecnico);
    }

    public void eliminar(Tecnico tecnico){
        tecnicos.remove(tecnico);
    }

    public static RepositoriosTecnicos getInstancia() {
        if(instancia == null) {
            instancia = new RepositoriosTecnicos();
        }
        return instancia;
    }

  }
