package modelo.broker;

public abstract class Broker {
    protected abstract void registrar(Suscriptor suscriptor);
    protected abstract void desregistrar(Suscriptor suscriptor);
}
