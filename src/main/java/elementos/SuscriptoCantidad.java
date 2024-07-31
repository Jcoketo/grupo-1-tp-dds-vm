package elementos;

import lombok.Getter;
import lombok.Setter;
import personas.Colaborador;

public class SuscriptoCantidad extends ColaboradorSuscripto {
    @Getter @Setter private Integer limiteViandasMinimas;
    @Getter @Setter private Integer limiteViandasMaximas;

    public SuscriptoCantidad(Heladera heladera, Colaborador colaborador, TipoSuscripcion tipo, Integer n) {
        super(heladera, colaborador, tipo);

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
