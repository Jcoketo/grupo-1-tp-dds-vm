package modelo.notificador;

import modelo.personas.Colaborador;

public interface StrategyNotificacion {
    public void enviarNotificacion(String mensaje, Colaborador persona, String asunto);
}
