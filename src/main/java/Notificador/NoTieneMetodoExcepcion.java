package Notificador;

public class NoTieneMetodoExcepcion extends RuntimeException {
    public NoTieneMetodoExcepcion() {
        super("El Colaborador no tiene un método de notificación asignado");
    }
}
