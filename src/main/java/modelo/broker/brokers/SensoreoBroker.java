package modelo.broker.brokers;

import modelo.broker.Broker;
import modelo.broker.Suscriptor;
import modelo.elementos.Sensoreo;

import java.util.ArrayList;
import java.util.List;

public class SensoreoBroker extends Broker {
    private List<Suscriptor> suscriptores = new ArrayList<>();

    @Override
    public void registrar(Suscriptor suscriptor) {
        suscriptores.add(suscriptor);
    }

    @Override
    public void desregistrar(Suscriptor suscriptor) {
        suscriptores.remove(suscriptor);
    }

    public void notificarSuscriptores(Sensoreo sensor) {
        for (Suscriptor suscriptor : suscriptores) {
            suscriptor.actualizar(sensor);
        }
    }
}
