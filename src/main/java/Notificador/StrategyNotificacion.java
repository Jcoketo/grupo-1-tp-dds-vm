package Notificador;

import personas.Colaborador;

public interface StrategyNotificacion {
    public void enviarNotificacion(String mensaje, Colaborador persona);
}
