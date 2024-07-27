package patronBroker;

import elementos.Alerta;
import elementos.Heladera;
import elementos.TipoAlerta;
import repositorios.RepositorioIncidentes;

public class BrokerMain {

    public void recibirAlerta(){
        Heladera heladera = new Heladera();
        heladera.marcarComoInactiva();
        Alerta alerta = new Alerta(TipoAlerta.FRAUDE, heladera);
        RepositorioIncidentes repo = RepositorioIncidentes.getInstancia();
        repo.agregar(alerta);
    }

    public void recibirSensoreo(String path, los datos){


    }
}
