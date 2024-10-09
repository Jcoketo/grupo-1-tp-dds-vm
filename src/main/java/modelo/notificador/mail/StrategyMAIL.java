package modelo.notificador.mail;

import modelo.notificador.StrategyNotificacion;
import modelo.personas.Colaborador;

public class StrategyMAIL implements StrategyNotificacion {
    private AdapterMAIL adapter;

    public void setAdapter(AdapterMAIL adapter){
        this.adapter = adapter;
    }

    @Override
    public void enviarNotificacion(String mensaje, Colaborador persona, String asunto) {
        this.adapter.enviarMAIL(mensaje, persona.getEmail(), asunto);
    }


}
