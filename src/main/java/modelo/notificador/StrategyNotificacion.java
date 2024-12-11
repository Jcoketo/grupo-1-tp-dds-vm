package modelo.notificador;

import modelo.personas.Colaborador;
import modelo.personas.MedioDeContacto;

public interface StrategyNotificacion {
    public void enviarNotificacion(String mensaje, MedioDeContacto medioDeContacto, String asunto);
}
