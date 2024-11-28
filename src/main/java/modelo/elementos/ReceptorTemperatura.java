package modelo.elementos;

import modelo.personas.Tecnico;
import persistencia.RepositorioIncidentes;
import persistencia.RepositoriosTecnicos;

public class ReceptorTemperatura {
    private Heladera heladera;
    private Sensoreo ultimoRegistro;

    public ReceptorTemperatura(Heladera heladera){
        this.heladera = heladera;
    }

    public void evaluar(Sensoreo sensor){ //TODO:TERMINAR

        if ( sensor.getFechaYhora().toLocalDate() != ultimoRegistro.getFechaYhora().toLocalDate() ){
            // Se reporta una FALLA DE CONEXION
            heladera.marcarComoInactiva();
            Alerta alerta = new Alerta(TipoAlerta.FALLA_EN_CONEXION, heladera);
            RepositorioIncidentes repo = RepositorioIncidentes.getInstancia();
            repo.agregar(alerta);
            RepositoriosTecnicos tecnicos = RepositoriosTecnicos.getInstancia();
            try {
                Tecnico tecnico = tecnicos.obtenerTecnicoCercano(heladera.getPuntoEstrategico().getAreas(), heladera);
                tecnico.notificarFalla(heladera, alerta);
            } catch (Exception e) { //TODO verificar como volver de este error
                System.out.println("No hay técnicos disponibles");
            }
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

    public void actualizar(Sensoreo sensor) {
        this.ultimoRegistro = sensor;
        evaluar(sensor);
    }

}