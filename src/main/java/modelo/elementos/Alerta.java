package modelo.elementos;

import javax.persistence.*;

import lombok.Getter;

@Entity
@DiscriminatorValue("ALERTA")
public class Alerta extends Incidente{
    @Enumerated(EnumType.STRING)
    @Getter private TipoAlerta tipoAlerta;

    public Alerta(TipoAlerta tipoAlerta, Heladera heladera){
        super(heladera);
        this.tipoAlerta = tipoAlerta;
    }

    public Alerta() {

    }
}
