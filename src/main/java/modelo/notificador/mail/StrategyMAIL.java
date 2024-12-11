package modelo.notificador.mail;

import modelo.notificador.StrategyNotificacion;
import modelo.personas.Colaborador;
import modelo.personas.MedioDeContacto;

public class StrategyMAIL implements StrategyNotificacion {
    private AdapterMAIL adapter;

    public void setAdapter(AdapterMAIL adapter){
        this.adapter = adapter;
    }

    @Override
    public void enviarNotificacion(String mensaje, MedioDeContacto medioDeContacto, String asunto) {
        this.adapter.enviarMAIL(mensaje, medioDeContacto.getContacto(), asunto);
    }


}
