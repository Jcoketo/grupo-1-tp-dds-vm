package notificador.whatsApp;

import notificador.StrategyNotificacion;
import personas.Colaborador;

public class StrategyWhatsApp implements StrategyNotificacion {
    private AdapterWhatsApp adapter;

    public void setAdapter(AdapterWhatsApp adapter){
        this.adapter = adapter;
    }

    @Override
    public void enviarNotificacion(String mensaje, Colaborador persona, String asunto) {
        this.adapter.enviarWhatsApp(mensaje, "");
    }
}
