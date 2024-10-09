package modelo.elementos;

import lombok.Getter;

public class Alerta extends Incidente{
    @Getter private TipoAlerta tipoAlerta;

    public Alerta(TipoAlerta tipoAlerta, Heladera heladera){
        super(heladera);
        this.tipoAlerta = tipoAlerta;
    }
}
