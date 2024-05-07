package Notificador.MAIL;

import Notificador.StrategyNotificacion;
import personas.Colaborador;

public class StrategyMAIL implements StrategyNotificacion {
    private AdapterMAIL adapter;

    public void setAdapter(AdapterMAIL adapter){
        this.adapter = adapter;
    }

    @Override
    public void enviarNotificacion(String mensaje, Colaborador persona) {
        this.adapter.enviarMAIL(mensaje, persona.getEmail());
    }


}
