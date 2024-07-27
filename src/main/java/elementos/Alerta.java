package elementos;

public class Alerta extends Incidente{
    private TipoAlerta tipoAlerta;

    public Alerta(TipoAlerta tipoAlerta, Heladera heladera){
        super(heladera);
        this.tipoAlerta = tipoAlerta;
    }
}
