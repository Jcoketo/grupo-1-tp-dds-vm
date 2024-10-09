package modelo.elementos;

import modelo.broker.Suscriptor;
import persistencia.RepositorioIncidentes;

public class ReceptorMovimiento implements Suscriptor {
    private Heladera heladera;

    public ReceptorMovimiento(Heladera heladera){
        this.heladera = heladera;
    }

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