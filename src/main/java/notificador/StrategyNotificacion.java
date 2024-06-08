package notificador;

import personas.Colaborador;

public interface StrategyNotificacion {
    public void enviarNotificacion(String mensaje, Colaborador persona, String asunto);
}
