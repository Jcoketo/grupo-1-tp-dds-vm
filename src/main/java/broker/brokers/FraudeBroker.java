package broker.brokers;

import broker.Broker;
import broker.Suscriptor;
import elementos.Sensoreo;

import java.util.ArrayList;
import java.util.List;

public class FraudeBroker extends Broker {
    private List<Suscriptor> suscriptores = new ArrayList<>();

    @Override
    public void registrar(Suscriptor suscriptor) {
        suscriptores.add(suscriptor);
    }

    @Override
    public void desregistrar(Suscriptor suscriptor) {
        suscriptores.remove(suscriptor);
    }

    public void notificarSuscriptores() {
        for (Suscriptor suscriptor : suscriptores) {
            suscriptor.actualizar();
        }
    }
}

