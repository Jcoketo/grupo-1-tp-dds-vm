package repositorios;

import elementos.Areas;
import java.util.ArrayList;
import personas.Tecnico;
import java.util.List;

public class RepositoriosTecnicos{
    private static RepositoriosTecnicos instancia = null;
    private List<Tecnico> tecnicos = new ArrayList<>();

    public static RepositoriosTecnicos getInstancia() {
        if(instancia == null) {
            instancia = new RepositoriosTecnicos();
        }
        return instancia;
    }

    public void agregar(Tecnico tecnico){
        tecnicos.add(tecnico);
    }

    public void eliminar(Tecnico tecnico){
        tecnicos.remove(tecnico);
    }

    public Tecnico obtenerTecnicoCercano(Areas area) {
        Tecnico tecnicoCercano = null;
        for (Tecnico tecnico : tecnicos) {
            if (tecnico.getAreaCobertura() == area) {
                tecnicoCercano = tecnico;
                this.eliminar(tecnico);
                this.agregar(tecnico);
                return tecnicoCercano;
            }
        }
        // Si no encontró uno en el área especificada, agarramos a uno "random":

        tecnicoCercano = this.tecnicos.get(0);
        this.eliminar(tecnicoCercano);
        this.agregar(tecnicoCercano);
        return tecnicoCercano;

    }

    public boolean hayTecnicos(){
        return !tecnicos.isEmpty();
    }

  }