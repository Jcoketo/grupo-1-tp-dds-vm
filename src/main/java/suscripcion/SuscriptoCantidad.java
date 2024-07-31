package suscripcion;

import elementos.Heladera;
import lombok.Getter;
import lombok.Setter;
import personas.Colaborador;
import personas.TipoMedioDeContacto;

public class SuscriptoCantidad extends ColaboradorSuscripto {
    @Getter @Setter private Integer limiteViandasMinimas;
    @Getter @Setter private Integer limiteViandasMaximas;

    public SuscriptoCantidad(Heladera heladera, Colaborador colaborador, TipoSuscripcion tipo, Integer n, TipoMedioDeContacto medio) {
        super(heladera, colaborador, tipo, medio);

        if (tipo == TipoSuscripcion.QUEDAN_POCAS){
            this.limiteViandasMinimas = n;
            this.limiteViandasMaximas = 0;
        } else if (tipo == TipoSuscripcion.POCO_ESPACIO){
            this.limiteViandasMinimas = 0;
            this.limiteViandasMaximas = n;
        }

        //this.limiteViandasMinimas = 0;
        //this.limiteViandasMaximas = 0;
    }

}
