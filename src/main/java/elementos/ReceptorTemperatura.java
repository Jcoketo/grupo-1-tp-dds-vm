package elementos;

public class ReceptorTemperatura {
    private Heladera heladera;
    private Sensoreo ultimoRegistro;

    public void evaluar(Sensoreo sensor){ //TODO:TERMINAR
        if(sensor.getTempRegistrada() < heladera.getTemperaturaMaxima()
            && sensor.getTempRegistrada() > heladera.getTemperaturaMinima()){
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
