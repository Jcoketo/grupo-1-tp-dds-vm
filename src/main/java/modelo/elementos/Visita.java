package modelo.elementos;

public class Visita {
    Heladera heladera;
    String descripcion;
    String URLfoto;
    Boolean incidenteSolucionado;

    public Visita(Heladera heladera, String descripcion, String URLfoto, Boolean incidenteSolucionado) {
        this.heladera = heladera;
        this.descripcion = descripcion;
        this.URLfoto = URLfoto;
        this.incidenteSolucionado = incidenteSolucionado;
    }
}
