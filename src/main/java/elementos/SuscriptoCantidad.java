package elementos;

import lombok.Getter;
import lombok.Setter;
import personas.Colaborador;

public class SuscriptoCantidad extends ColaboradorSuscripto {
    @Getter @Setter private Integer limiteViandasMinimas;
    @Getter @Setter private Integer limiteViandasMaximas;

    public SuscriptoCantidad(Heladera heladera, Colaborador colaborador, TipoSuscripcion tipo) {
        super(heladera, colaborador, tipo);
        this.limiteViandasMinimas = 0;
        this.limiteViandasMaximas = 0;
    }

}
