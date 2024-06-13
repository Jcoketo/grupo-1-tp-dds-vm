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
    }

    public String getTipo(){
        //TODO
        return "tipo";
    }

}