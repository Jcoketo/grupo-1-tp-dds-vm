package Notificador.MAIL;

import Notificador.StrategyNotificacion;

public class StrategyMAIL implements StrategyNotificacion {
    private AdapterMAIL adapter;

    public void setAdapter(AdapterMAIL adapter){
        this.adapter = adapter;
    }

    @Override
    public void enviarNotificacion(String mensaje, Persona persona) {
        this.adapter.enviarMAIL(mensaje, persona.getEmail());
    }
}
