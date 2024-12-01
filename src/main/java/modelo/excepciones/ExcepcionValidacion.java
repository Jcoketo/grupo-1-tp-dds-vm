package modelo.excepciones;

public class ExcepcionValidacion extends RuntimeException {
    public ExcepcionValidacion(String message) {
        super(message);
    }
}
