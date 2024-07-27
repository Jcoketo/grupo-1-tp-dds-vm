package elementos;

import broker.Suscriptor;
import repositorios.RepositorioIncidentes;

public class ReceptorMovimiento implements Suscriptor {
    private Heladera heladera;

    public void recibirAlerta(){
        heladera.marcarComoInactiva();
        Alerta alerta = new Alerta(TipoAlerta.FRAUDE, heladera);
        RepositorioIncidentes repo = RepositorioIncidentes.getInstancia();
        repo.agregar(alerta);
    }

    @Override
    public void actualizar(Sensoreo sensor) {

    }
}