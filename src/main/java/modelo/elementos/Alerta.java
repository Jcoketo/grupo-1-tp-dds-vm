package modelo.elementos;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Getter;

@Entity
public class Alerta extends Incidente{
    @Enumerated(EnumType.STRING)
    @Getter private TipoAlerta tipoAlerta;

    public Alerta(TipoAlerta tipoAlerta, Heladera heladera){
        super(heladera);
        this.tipoAlerta = tipoAlerta;
    }
}
