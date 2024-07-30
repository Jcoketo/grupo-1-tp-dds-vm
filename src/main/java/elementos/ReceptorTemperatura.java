package elementos;

import broker.Suscriptor;
import broker.brokers.SensoreoBroker;
import personas.Tecnico;
import repositorios.RepositorioIncidentes;
import repositorios.RepositoriosTecnicos;

public class ReceptorTemperatura implements Suscriptor {
    private Heladera heladera;
    private Sensoreo ultimoRegistro;
    private SensoreoBroker broker = new SensoreoBroker();

    public ReceptorTemperatura(Heladera heladera){
        this.heladera = heladera;
        broker.registrar(this);
    }

    public void evaluar(Sensoreo sensor){ //TODO:TERMINAR

        if ( sensor.getFechaYhora().toLocalDate() != ultimoRegistro.getFechaYhora().toLocalDate() ){
            // Se reporta una FALLA DE CONEXION
            heladera.marcarComoInactiva();
            Alerta alerta = new Alerta(TipoAlerta.FALLA_EN_CONEXION, heladera);
            RepositorioIncidentes repo = RepositorioIncidentes.getInstancia();
            repo.agregar(alerta);
            RepositoriosTecnicos tecnicos = RepositoriosTecnicos.getInstancia();
            Tecnico tecnico = tecnicos.obtenerTecnicoCercano(heladera.getPuntoEstrategico().getAreas());

            tecnico.notificarFalla(heladera, alerta);

            return;
        }

        if(sensor.getTempRegistrada() <= heladera.getTemperaturaMaxima()
            && sensor.getTempRegistrada() >= heladera.getTemperaturaMinima()){
            return;
        }
        else {
            heladera.marcarComoInactiva();
            Alerta alerta = new Alerta(TipoAlerta.FALLA_TEMPERATURA, heladera);
            RepositorioIncidentes repo = RepositorioIncidentes.getInstancia();
            repo.agregar(alerta);
        }
    }

   /* public void leerSensoreo(String path){
        // recibirlo desde el broker
        //TODO
        // cuando lee uno nuevo, corrobora que la hora no sea igual a la ultima registrada
        // en caso que sea igual, se reporta una FALLA DE CONEXION
    }*/

    @Override
    public void actualizar(Sensoreo sensor) {
        this.ultimoRegistro = sensor;
        evaluar(sensor);
    }


}