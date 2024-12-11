package modelo.notificador.whatsApp;

import modelo.notificador.StrategyNotificacion;
import modelo.personas.MedioDeContacto;

public class StrategyWhatsApp implements StrategyNotificacion {
    private AdapterWhatsApp adapter;

    public void setAdapter(AdapterWhatsApp adapter){
        this.adapter = adapter;
    }

    @Override
    public void enviarNotificacion(String mensaje, MedioDeContacto medioDeContacto, String asunto) {
        this.adapter.enviarWhatsApp(mensaje, medioDeContacto.getContacto());
    }
}
