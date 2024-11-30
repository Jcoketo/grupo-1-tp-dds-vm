package modelo.elementos;

import persistencia.RepositorioIncidentes;

public class ReceptorMovimiento {
    private Heladera heladera;

    public ReceptorMovimiento(Heladera heladera){
        this.heladera = heladera;
    }

    public void recibirAlerta(){
        heladera.marcarComoInactiva();
        Alerta alerta = new Alerta(TipoAlerta.FRAUDE, heladera);
        RepositorioIncidentes repo = RepositorioIncidentes.getInstancia();
        repo.agregarIncidente(alerta);
    }

    public void actualizar(Sensoreo sensor) {

    }
}