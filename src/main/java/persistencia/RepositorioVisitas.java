package persistencia;

import modelo.elementos.Visita;
import java.util.ArrayList;
import java.util.List;

public class RepositorioVisitas {
    private static RepositorioVisitas instancia = null;

    private static List<Visita> visitas;

    private RepositorioVisitas() {
        visitas = new ArrayList<Visita>();
    }
    public static RepositorioVisitas getInstancia() {
        if(instancia == null) {
            instancia = new RepositorioVisitas();
        }
        return instancia;
    }

    public void agregar(Visita visita) {
        visitas.add(visita);
    }


}
