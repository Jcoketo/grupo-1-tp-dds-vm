package elementos;

public class ReceptorTemperatura {
    private Heladera heladera;
    private Sensoreo ultimoRegistro;

    public void evaluar(Sensoreo sensor){ //TODO:TERMINAR
        if(sensor.getTempRegistrada() < heladera.getTemperaturaMaxima()
            && sensor.getTempRegistrada() > heladera.getTemperaturaMinima()){
            // No hacemos nada si la temperatura se encuentra dentro de los límites.
        }
        else {
            // No sé aclara qué hacer en caso de que la temperatura registrada esté fuera de los límites.
        }
    }

    public void leerSensoreo(){
        //TODO
        // cuando lee uno nuevo, corrobora que la hora no sea igual a la ultima registrada
        // en caso que sea igual, se reporta una FALLA DE CONEXION
    }

    public String getTipo(){
        //TODO
        return "tipo";
    }

}