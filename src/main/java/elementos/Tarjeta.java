package elementos;

import repositorios.RepositorioTarjetas;

import java.util.ArrayList;
import java.util.List;

public class Tarjeta {
    protected String id;
    protected List<UsoTarjetaPlastica> historialDeUsos;
    protected Boolean recibida;

    public Tarjeta(){
        RepositorioTarjetas repositorioTarjetas = RepositorioTarjetas.getInstancia();
        this.id = repositorioTarjetas.generarIdTarjeta();
        this.historialDeUsos = new ArrayList<UsoTarjetaPlastica>();
        this.recibida = false;
    }

    public void registrarUso(Heladera heladera){
        this.historialDeUsos.add(new UsoTarjetaPlastica(heladera));
    }

    public void recibida(){
        this.recibida = true;
    }
}
